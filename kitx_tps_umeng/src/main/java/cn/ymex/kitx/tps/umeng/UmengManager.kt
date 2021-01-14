package cn.ymex.kitx.tps.umeng

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import android.util.Log
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.*
import com.umeng.message.entity.UMessage
import org.android.agoo.huawei.HuaWeiRegister
import org.android.agoo.mezu.MeizuRegister
import org.android.agoo.oppo.OppoRegister
import org.android.agoo.vivo.VivoRegister
import org.android.agoo.xiaomi.MiPushRegistar


class UmengManager {
    private val TAG  = "UManager-UMENG"
    private lateinit var context: Application
    private lateinit var config :UmengConfig
    private lateinit var pushAgent:PushAgent
    private val pushConfigs = arrayListOf<PushConfig>()
    private var debugPush = true
    companion object {

        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            UmengManager()
        }
    }

    fun register(context: Application, config: UmengConfig, debug : Boolean, vararg pushConfigs: PushConfig) {
        this.debugPush = debug
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
        UMConfigure.setLogEnabled(debug)

        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)

        //获取消息推送代理示例
        pushAgent = PushAgent.getInstance(context)
        pushAgent.resourcePackageName = this.javaClass.`package`!!.name
        pushAgent.setNotificaitonOnForeground(true)


        //自定义通知栏
        pushAgent.messageHandler = object : UmengMessageHandler(){

//      自定义通知栏
//            override fun getNotification(context: Context, message: UMessage): Notification {
//                return super.getNotification(context, message)
//            }

            override fun dealWithCustomMessage(context: Context, message: UMessage) {
                super.dealWithCustomMessage(context, message)
                Log.i(TAG, "自定义通知栏：dealWithCustomMessage-------->  $message")
            }

            override fun dealWithNotificationMessage(context: Context, message: UMessage) {
                Log.i(TAG, "自定义通知栏：dealWithNotificationMessage-------->  $message")
                super.dealWithNotificationMessage(context, message)
            }
        }

        //通知栏打开动作:仅限自定义行为
        pushAgent.notificationClickHandler = object :UmengNotificationClickHandler(){
            override fun dealWithCustomAction(context: Context, message: UMessage) {
                Log.i(TAG, "自定义通知栏2：getNotification-------->  $message")
                super.dealWithCustomAction(context,message)
            }
        }


        pushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                if (debugPush){
                    Log.i("UMENG", "注册成功：deviceToken：-------->  $deviceToken")
                }
            }

            override fun onFailure(p0: String, p1: String) {
                if (debugPush){
                    Log.i("UMENG", "注册失败：--------->$p0   $p1")
                }

            }
        })




        /**
         * 初始化厂商通道
         */
        this.pushConfigs.forEach {
            when(it.brand){
                PushBrand.XIAOMI ->{//小米通道
                    MiPushRegistar.register(context, it.id, it.key)
                }
                PushBrand.HUAWEI ->{//华为通道，初始化参数在minifest中配置
                    HuaWeiRegister.register(context)
                }
                PushBrand.MEIZU  ->{//魅族通道
                    MeizuRegister.register(context, it.id, it.key)
                }
                PushBrand.OPPO   ->{//OPPO通道
                    OppoRegister.register(context, it.key, it.secret)
                }
                PushBrand.VIVO   ->{//VIVO 通道，初始化参数在minifest中配置
                    VivoRegister.register(context)
                }
            }
        }

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

    fun statisticsPush(){
        PushAgent.getInstance(context).onAppStart()
    }

    /**
     * 在AUTO或MANUAL模式下，如果需要对非Activity页面，如Fragment、自定义View等非标准页面进行统计。
     * 需要通过MobclickAgent.onPageStart/MobclickAgent.onPageEnd接口在合适的时机进行页面统计。
     */

    fun statisticsPageStart(view:String){
        MobclickAgent.onPageStart(view);
    }

    fun statisticsPageEnd(view:String){
        MobclickAgent.onPageEnd(view);
    }


    /**
     * 选用MANUAL页面采集模式,
     * 对宿主App中所有Activity都手动调用MobclickAgent.onResume/MobclickAgent.onPause手动埋点。
     */

    fun statisticsOnResume(context: Context){
        MobclickAgent.onResume(context)
    }

    fun statisticsOnPause(context: Context){
        MobclickAgent.onPause(context)
    }

    /**
     * 设置别名
     */
    fun setAlias(alias: String, des:String = "app",block:(ok:Boolean,message:String)->Unit) {
        PushAgent.getInstance(context).setAlias(alias,des,block)
    }

    /**
     * 添加别名
     */
    fun addAlias(alias: String, des:String = "app",block:(ok:Boolean,message:String)->Unit) {
        pushAgent.addAlias(alias,des,block)
    }


    fun unsetAlias(alias: String,des:String = "app",block:(ok:Boolean,message:String)->Unit) {
        pushAgent.deleteAlias(alias,des,block)
    }


    /**
     * 设置标签
     */
    fun subscribe(topic: String,block: (ok: Boolean, message: String) -> Unit) {
        pushAgent.tagManager.addTags({ p0, p1 -> block(p0,p1.jsonString) },topic)
    }

    fun unsubscribe(topic: String,block: (ok: Boolean, message: String) -> Unit) {
        pushAgent.tagManager.deleteTags({p0,p1 -> block(p0,p1.jsonString)},topic)
    }

    /**
     * 暂停接收通知
     */
    fun pausePush(success:()->Unit = {},failure:(String,String)->Unit = {_,_->}) {
        pushAgent.disable(object : IUmengCallback{
            override fun onSuccess() {
                success()
            }

            override fun onFailure(p0: String, p1: String) {
                failure(p0,p1)
            }
        })
    }


    fun resumePush(success:()->Unit = {},failure:(String,String)->Unit = {_,_->}) {
        pushAgent.enable(object : IUmengCallback{
            override fun onSuccess() {
                success()
            }

            override fun onFailure(p0: String, p1: String) {
                failure(p0,p1)
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

}