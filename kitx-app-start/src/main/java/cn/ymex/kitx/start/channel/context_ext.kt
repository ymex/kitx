package cn.ymex.kitx.start.channel

import android.content.Context


/**
 * 获取 walle 写入的 channel
 */
fun Context.getChannel(defChannel:String = "android"):String{
    return WalleChannelReader.getChannel(this,defChannel)?:defChannel
}