package cn.ymex.kitx.tps.umeng

import com.umeng.message.entity.UMessage


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

open class PushConfig(val brand: PushBrand, val id:String = "", val key:String = "", val secret:String = "")



class PushMessage(val body: UMessage){
    override fun toString(): String {
        return body.raw.toString()
    }
}