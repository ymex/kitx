package cn.ymex.kitx.start.app

import android.os.Bundle
import cn.ymex.kitx.core.app.ViewModelActivity
import cn.ymex.kitx.start.eventbus.EventBusConfig
import cn.ymex.kitx.start.eventbus.EventBusProxy
import cn.ymex.kitx.start.eventbus.EventMessage


open class StartActivity : ViewModelActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initEventBus("onCreate")
    }

    override fun onStart() {
        super.onStart()
        initEventBus("onStart")
    }

    override fun onResume() {
        super.onResume()
        initEventBus("onResume")
    }

    override fun onPause() {
        super.onPause()
        initEventBus("onPause")
    }

    override fun onStop() {
        super.onStop()
        initEventBus("onStop")
    }


    override fun onDestroy() {
        super.onDestroy()
        initEventBus("onDestroy")
    }

    private fun initEventBus(method: String) {
        val ebc = enableEventBus()
        if (ebc.enable && ebc.register == method) {
            EventBusProxy.register(this)
        }
        if (ebc.enable && ebc.unregister == method) {
            EventBusProxy.unregister(this)
        }
    }

    /**
     * user @Subscribe
     */
    open fun doEventBusSubscribe(message: EventMessage) {

    }

    open fun enableEventBus(): EventBusConfig {
        return EventBusConfig()
    }

}