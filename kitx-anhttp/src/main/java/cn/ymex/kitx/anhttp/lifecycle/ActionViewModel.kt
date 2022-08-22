package cn.ymex.kitx.anhttp.lifecycle

import android.os.Bundle
import cn.ymex.kitx.core.lifecycle.MutableLifeData

open class ActionViewModel : LifeViewModel() {
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

    /**
     * 状态消息
     */
    private val _stateLiveData = MutableLifeData<StateData>()
    val stater = _stateLiveData

    fun sendState(stateData: StateData) {
        stater.postValue(stateData)
    }

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


/**
 * 协和执行状态状态
 */
enum class LaunchStatus {
    START, //开始
    COMPLETE, //正常完成
    FAILURE //错误
}

data class StateData(
    var status: String,
    var throwable: Throwable? = null,
    var bundle: Bundle = Bundle.EMPTY
) {
    override fun toString(): String {
        return "StateData(status=$status, message='$throwable', bundle='${bundle}')"
    }
}