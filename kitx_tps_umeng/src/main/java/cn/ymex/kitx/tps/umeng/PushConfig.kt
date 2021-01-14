package cn.ymex.kitx.tps.umeng;


/***
 * 厂商推送
 */
enum class PushBrand{
    XIAOMI,
    HUAWEI,
    VIVO,
    OPPO,
    MEIZU
}

open class PushConfig(val brand:PushBrand, val id:String = "", val key:String = "",val secret:String = "")