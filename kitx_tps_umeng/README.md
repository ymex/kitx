## 厂商渠道

仓库加入：

```
 repositories {
        google()
        jcenter()
        maven { url 'https://dl.bintray.com/umsdk/release' }
    }
```

vivo 与华为推送的参数 要在 AndroidManifest.xml中配置，其他的厂商参数 在代码里配置。 

```xml
<!-- VIVO厂商通道-->
<meta-data
    android:name="com.vivo.push.api_key"
    android:value="此处改为VIVO后台真实参数" />
<meta-data
    android:name="com.vivo.push.app_id"
    android:value="此处改为VIVO后台真实参数" />

<!-- HUAWEI厂商通道 -->
<meta-data
    android:name="com.huawei.hms.client.appid"
    android:value="此处改为huawei后台真实参数" />
```

## 友盟厂商离线推送的Activity
要配置为`cn.ymex.kitx.tps.umeng.PushHoldActivity` 
若要自定义厂商的离线推送、在`UmengManager.MessageHandler `里处理。


```
## 使用
class UmengNotification {
    companion object {
        fun init(context: Application) {
            val TAG = "UManager-UMENG"
            val umc =
                UmengConfig("xxxxx", "xxxx", "local")
            val pcg = arrayListOf(
                PushConfig(PushBrand.XIAOMI, "xxxx", "xxx"),
                PushConfig(PushBrand.HUAWEI)
            )
            UmengManager.instance.registerCallback = { flag, info ->
                Log.i(TAG, "注册结果：${flag}：-------->  $info")
            }
            UmengManager.instance.notification = { _, message ->
                Log.i(TAG, "自定义通知栏：getNotification-------->  $message")
                null
            }
            UmengManager.instance.messageHandler = object : UmengManager.MessageHandler {
                override fun dealWithMessage(context: Context, type: String, message: PushMessage) {
                    Log.i(TAG, "消息处理：dealWithMessage--------> $type $message")
                }
                override fun notificationClickCustomAction(context: Context, message: PushMessage) {
                    Log.i(TAG, "消息处理：notificationClickCustomAction-------->  $message")
                }
                override fun dealWithNativeMessage(context: Context, message: PushMessage): Boolean {
                    Log.i(TAG, "消息处理：dealWithNativeMessage--------> $message")
                    return true
                }
            }
            UmengManager.instance.register(
                context,
                umc, true, * pcg.toTypedArray()
            )
        }
    }
}
```