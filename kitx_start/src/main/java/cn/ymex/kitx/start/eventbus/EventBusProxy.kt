package cn.ymex.kitx.start.eventbus

import org.greenrobot.eventbus.EventBus


object EventBusProxy {
    fun register(any: Any) {
        EventBus.getDefault().register(any)
    }

    fun unregister(any: Any) {
        EventBus.getDefault().unregister(any)
    }

    fun post(message: EventMessage) {
        EventBus.getDefault().post(message)
    }
}