package cn.ymex.kitx.start.app

import android.content.Context
import android.os.Bundle
import android.view.View
import cn.ymex.kitx.core.app.ViewModelFragment
import cn.ymex.kitx.start.eventbus.BroadcastConfig
import cn.ymex.kitx.start.eventbus.Broadcast
import cn.ymex.kitx.start.eventbus.BroadCastMessage


open class StartFragment : ViewModelFragment(){

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initEventBus("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initEventBus("onCreate")
    }

    override fun onCreateView(savedInstanceState: Bundle?): Int {
        initEventBus("onCreateView")
        return super.onCreateView(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initEventBus("onActivityCreated")
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

    override fun onDestroyView() {
        super.onDestroyView()
        initEventBus("onDestroyView")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventBus("onViewCreated")
    }

    override fun onDestroy() {
        super.onDestroy()
        initEventBus("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        initEventBus("onDetach")
    }


    private fun initEventBus(method: String) {
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
        return BroadcastConfig(register = "onViewCreated", unregister = "onDestroyView")
    }
}