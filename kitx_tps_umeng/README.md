



## 厂商渠道

仓库加入：

```
repositories {
    google()
    jcenter()
    // 华为源（华为厂商通道）
    maven { url 'https://developer.huawei.com/repo/'}
    maven { url 'https://dl.bintray.com/umsdk/release' }
}


dependencies {
    // 华为厂商通道插件
    classpath 'com.huawei.agconnect:agcp:1.4.2.300'
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



