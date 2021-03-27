package cn.ymex.kitx.start.eventbus

import org.greenrobot.eventbus.EventBus


object Broadcast {
    fun register(any: Any) {
        EventBus.getDefault().register(any)
    }

    fun unregister(any: Any) {
        EventBus.getDefault().unregister(any)
    }

    fun post(message: BroadCastMessage) {
        EventBus.getDefault().post(message)
    }
}