package cn.ymex.kitx.start.eventbus

/**
 * @param enable 是否开启 eventbus
 * @param register 注册在哪个方法，写activity / fragment生命周期的方法名
 * @param unregister 在哪个方法取消注册，写activity /fragment 生命周期的方法名
 */
data class BroadcastConfig(var enable:Boolean = false, var register:String = "onCreate", var unregister:String="onDestroy")