package cn.ymex.kitx.anhttp.lifecycle

import android.os.Bundle
import cn.ymex.kitx.core.lifecycle.MutableLifeData


/**
 * 视图状态
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

/**
 * 状态
 */
open class StateViewModel : LifeViewModel() {
    private val _stateLiveData = MutableLifeData<StateData>()
    val stater = _stateLiveData

    fun sendState(stateData: StateData) {
        stater.postValue(stateData)
    }
}