package cn.ymex.kitx.tps.umeng

import android.app.ActivityManager
import android.app.Application
import android.app.Notification
import android.content.Context
import android.content.SharedPreferences
import android.os.Process
import com.taobao.accs.ACCSClient
import com.taobao.accs.AccsClientConfig
import com.taobao.agoo.TaobaoRegister
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.commonsdk.utils.UMUtils
import com.umeng.message.*
import com.umeng.message.entity.UMessage
import org.android.agoo.huawei.HuaWeiRegister
import org.android.agoo.mezu.MeizuRegister
import org.android.agoo.oppo.OppoRegister
import org.android.agoo.vivo.VivoRegister
import org.android.agoo.xiaomi.MiPushRegistar


class UmengManager {
    private lateinit var preferences: SharedPreferences
    private lateinit var context: Application
    private lateinit var config: UmengConfig
    private lateinit var pushAgent: PushAgent
    private val pushConfigs = arrayListOf<PushConfig>()
    private var debugPush = true

    //消息处理
    var messageHandler: MessageHandler? = null

    //自定义通知视图
    var notification: (context: Context, message: PushMessage) -> Notification? = { _, _ -> null }

    //注册回调
    var registerCallback: (success: Boolean, info: String) -> Unit = { _, _ -> }

    companion object {

        private const val NAME = "kitx_um_settings"
        private const val KEY_PRIVACY_AGREEMENT = "privacy_agreement"

        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            UmengManager()
        }
    }

    fun init(context: Application):UmengManager{
        this.context = context
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
        return this
    }

    /**
     * 设置隐私协议是否同意
     *
     * @param value 是否同意
     */
    fun setAgreePrivacyAgreement(value: Boolean) {
        preferences.edit().putBoolean(KEY_PRIVACY_AGREEMENT, value).apply()
    }

    /**
     * 是否同意了隐私协议
     *
     * @return true 已经同意；false 还没有同意
     */
    fun hasAgreePrivacyAgreement(): Boolean {
        return preferences.getBoolean(KEY_PRIVACY_AGREEMENT, false)
    }

    /**
     * 预初始化。已添加子进程中初始化sdk。
     * 使用场景：用户未同意隐私政策协议授权时，延迟初始化
     * @param config  友盟的配置
     * @param pushConfigs 厂商通道的配置
     */
    fun preRegister(
        config: UmengConfig,
        vararg pushConfigs: PushConfig
    ){
        try {
            //解决厂商通道推送乱码问题
            val builder = AccsClientConfig.Builder()
            builder.setAppKey("umeng:" + config.key)
            builder.setAppSecret(config.messageSecret)
            builder.setTag(AccsClientConfig.DEFAULT_CONFIGTAG)
            ACCSClient.init(context, builder.build())
            TaobaoRegister.setAccsConfigTag(context, AccsClientConfig.DEFAULT_CONFIGTAG)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        UMConfigure.preInit(context, config.key, config.channel)
        if (!isMainProcess(context)){
            register(config, * pushConfigs)
        }
    }

    /**
     * 初始化。
     * 场景：用户已同意隐私政策协议授权时
     * @param config  友盟的配置
     * @param pushConfigs 厂商通道的配置
     * 注意： 在要主进程中初始化
     */
    fun register(
        config: UmengConfig,
        vararg pushConfigs: PushConfig
    ) {
        this.debugPush = config.debug
        this.context = context
        this.config = config
        this.pushConfigs.clear()
        this.pushConfigs.addAll(pushConfigs)

        UMConfigure.init(
            context,
            config.key,//应用申请的Appkey
            config.channel,//渠道名称
            // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；
            // 传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
            UMConfigure.DEVICE_TYPE_PHONE,
            config.messageSecret//Push推送业务的secret 填充Umeng Message Secret对应信息
        )

        //toast 日志
        UMConfigure.setLogEnabled(this.debugPush)

        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)

        //获取消息推送代理示例
        pushAgent = PushAgent.getInstance(context)
        pushAgent.resourcePackageName = this.javaClass.`package`!!.name
//        pushAgent.setNotificaitonOnForeground(true)

        //自定义通知栏
        pushAgent.messageHandler = object : UmengMessageHandler() {

            //自定义通知栏
            override fun getNotification(context: Context, message: UMessage): Notification? {
                return notification(context, PushMessage(message))
            }

            override fun dealWithCustomMessage(context: Context, message: UMessage) {
                super.dealWithCustomMessage(context, message)
                messageHandler?.dealWithMessage(context, message.display_type, PushMessage(message))
            }

            override fun dealWithNotificationMessage(context: Context, message: UMessage) {
                super.dealWithNotificationMessage(context, message)
                messageHandler?.dealWithMessage(context, message.display_type, PushMessage(message))
            }
        }

        //通知栏打开动作:仅限自定义行为
        pushAgent.notificationClickHandler = object : UmengNotificationClickHandler() {
            override fun dealWithCustomAction(context: Context, message: UMessage) {
                messageHandler?.notificationClickCustomAction(context, PushMessage(message))
                super.dealWithCustomAction(context, message)
            }
        }

        //注册推送服务，每次调用register方法都会回调该接口
        pushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                registerCallback(true, deviceToken)
            }

            override fun onFailure(p0: String, p1: String) {
                registerCallback(false, "$p0,$p1")

            }
        })

        /**
         * 初始化厂商通道
         */
        if (isMainProcess(context)) {
            this.pushConfigs.forEach {
                when (it.brand) {
                    //小米通道，填写您在小米后台APP对应的xiaomi id和key
                    PushBrand.XIAOMI -> {
                        MiPushRegistar.register(context, it.id, it.key)
                    }
                    //华为，注意华为通道的初始化参数在minifest中配置
                    PushBrand.HUAWEI -> {
                        HuaWeiRegister.register(context)
                    }
                    //魅族，填写您在魅族后台APP对应的app id和key
                    PushBrand.MEIZU -> {//魅族通道
                        MeizuRegister.register(context, it.id, it.key)
                    }
                    //OPPO，填写您在OPPO后台APP对应的app key和secret
                    PushBrand.OPPO -> {//OPPO通道
                        OppoRegister.register(context, it.key, it.secret)
                    }
                    //VIVO 通道，初始化参数在minifest中配置
                    PushBrand.VIVO -> {
                        VivoRegister.register(context)
                    }
                }
            }
        }

    }

    /**
     * 是否运行在主进程
     *
     * @param context 应用上下文
     * @return true: 主进程；false: 子进程
     */
    fun isMainProcess(context: Context?): Boolean {
        return UMUtils.isMainProgress(context)
    }

    private fun shouldInit(context: Application): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processInfo = am.runningAppProcesses
        val mainProcessName: String = context.packageName
        val myPid = Process.myPid()
        for (info in processInfo) {
            if (info.pid == myPid && mainProcessName == info.processName) {
                return true
            }
        }
        return false
    }

    /**
     * 该方法是【友盟+】Push后台进行日活统计及多维度推送的必调用方法，请务必调用！
     * 应用数据统计接口 在所有的Activity 的onCreate 方法或在应用的BaseActivity的onCreate方法中添加：
     */

    fun statisticsPush() {
        PushAgent.getInstance(context).onAppStart()
    }

    /**
     * 在AUTO或MANUAL模式下，如果需要对非Activity页面，如Fragment、自定义View等非标准页面进行统计。
     * 需要通过MobclickAgent.onPageStart/MobclickAgent.onPageEnd接口在合适的时机进行页面统计。
     */

    fun statisticsPageStart(view: String) {
        MobclickAgent.onPageStart(view);
    }

    fun statisticsPageEnd(view: String) {
        MobclickAgent.onPageEnd(view);
    }


    /**
     * 选用MANUAL页面采集模式,
     * 对宿主App中所有Activity都手动调用MobclickAgent.onResume/MobclickAgent.onPause手动埋点。
     */

    fun statisticsOnResume(context: Context) {
        MobclickAgent.onResume(context)
    }

    fun statisticsOnPause(context: Context) {
        MobclickAgent.onPause(context)
    }

    /**
     * 设置别名
     */
    fun setAlias(
        alias: String,
        des: String = "app",
        block: (ok: Boolean, message: String) -> Unit
    ) {
        PushAgent.getInstance(context).setAlias(alias, des, block)
    }

    /**
     * 添加别名
     */
    fun addAlias(
        alias: String,
        des: String = "app",
        block: (ok: Boolean, message: String) -> Unit
    ) {
        pushAgent.addAlias(alias, des, block)
    }


    fun unsetAlias(
        alias: String,
        des: String = "app",
        block: (ok: Boolean, message: String) -> Unit
    ) {
        pushAgent.deleteAlias(alias, des, block)
    }


    /**
     * 设置标签
     */
    fun subscribe(topic: String, block: (ok: Boolean, message: String) -> Unit) {
        pushAgent.tagManager.addTags({ p0, p1 -> block(p0, p1.jsonString) }, topic)
    }

    fun unsubscribe(topic: String, block: (ok: Boolean, message: String) -> Unit) {
        pushAgent.tagManager.deleteTags({ p0, p1 -> block(p0, p1.jsonString) }, topic)
    }

    /**
     * 暂停接收通知
     */
    fun pausePush(success: () -> Unit = {}, failure: (String, String) -> Unit = { _, _ -> }) {
        pushAgent.disable(object : IUmengCallback {
            override fun onSuccess() {
                success()
            }

            override fun onFailure(p0: String, p1: String) {
                failure(p0, p1)
            }
        })
    }


    fun resumePush(success: () -> Unit = {}, failure: (String, String) -> Unit = { _, _ -> }) {
        pushAgent.enable(object : IUmengCallback {
            override fun onSuccess() {
                success()
            }

            override fun onFailure(p0: String, p1: String) {
                failure(p0, p1)
            }
        })
    }


    private fun shouldInit(context: Context): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processInfo = am.runningAppProcesses
        val mainProcessName: String = context.packageName
        val myPid = Process.myPid()
        for (info in processInfo) {
            if (info.pid == myPid && mainProcessName == info.processName) {
                return true
            }
        }
        return false
    }


    interface MessageHandler {
        /**
         * 处理收到的消息
         * @param type 消息类型 、、notification 、、message、、native
         * @return bool
         */
        fun dealWithMessage(context: Context, type: String, message: PushMessage)

        /**
         * 厂商通知点击时处理
         * @return true 自动处理
         */
        fun dealWithNativeMessage(context: Context, message: PushMessage): Boolean

        /**
         * 通知栏打开动作:仅限自定义行为
         */
        fun notificationClickCustomAction(context: Context, message: PushMessage)
    }
}