package cn.ymex.tps.umeng

//替换为Appkey,服务后台位置：应用管理 -> 应用信息 -> Appkey
//Push推送业务的secret 填充Umeng Message Secret对应信息
//渠道
open class UmengConfig(
    val key: String,
    var messageSecret: String = "",
    val channel: String,
)

