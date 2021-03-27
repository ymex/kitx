package cn.ymex.kitx.start.app

import android.os.Bundle
import cn.ymex.kitx.core.app.ViewModelActivity
import cn.ymex.kitx.start.eventbus.BroadcastConfig
import cn.ymex.kitx.start.eventbus.Broadcast
import cn.ymex.kitx.start.eventbus.BroadCastMessage


open class StartActivity : ViewModelActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configEventBus("onCreate")
    }

    override fun onStart() {
        super.onStart()
        configEventBus("onStart")
    }

    override fun onResume() {
        super.onResume()
        configEventBus("onResume")
    }

    override fun onPause() {
        super.onPause()
        configEventBus("onPause")
    }

    override fun onStop() {
        super.onStop()
        configEventBus("onStop")
    }


    override fun onDestroy() {
        super.onDestroy()
        configEventBus("onDestroy")
    }

    private fun configEventBus(method: String) {
        val ebc = enableEventBus()
        if (ebc.enable && ebc.register == method) {
            Broadcast.register(this)
        }
        if (ebc.enable && ebc.unregister == method) {
            Broadcast.unregister(this)
        }
    }

    /**
     * user @Subscribe
     */
    open fun doEventBusSubscribe(message: BroadCastMessage) {

    }

    open fun enableEventBus(): BroadcastConfig {
        return BroadcastConfig()
    }

}