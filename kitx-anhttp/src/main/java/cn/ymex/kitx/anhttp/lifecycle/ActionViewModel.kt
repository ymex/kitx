package cn.ymex.kitx.anhttp.lifecycle

import android.os.Bundle
import cn.ymex.kitx.core.lifecycle.MutableLifeData

open class ActionViewModel : StateViewModel() {
    /**
     * 路由消息
     */
    private val _intentRouter = MutableLifeData<IntentRouter>()
    val router = _intentRouter

    /**
     * toast提示消息
     */
    private val _toastLiveData = MutableLifeData<String>()
    val toaster = _toastLiveData


    fun sendAction(intentRouter: IntentRouter) {
        router.postValue(intentRouter)
    }

    fun sendToast(message: String) {
        toaster.postValue(message)
    }
}

/**
 * 需要页面跳转时使用
 */

data class IntentRouter(
    val path: String,
    val bundle: Bundle = Bundle(),
    val result: Boolean = false,
    val requestCode: Int = 0,
    val finish: Boolean = false,
)