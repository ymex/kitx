package cn.ymex.kitx.anhttp.lifecycle

import android.os.Bundle
import androidx.lifecycle.ViewModel
import cn.ymex.kitx.core.lifecycle.MutableLifeData



/**
 * 协和执行状态状态
 */
object LaunchStatus {
    const val START  = "an_http_start" //开始
    const val COMPLETE = "an_http_complete" //正常完成
    const val FAILURE = "an_http_failure" //错误
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
    /**
     * 状态消息
     */
    private val _stateLiveData = MutableLifeData<StateData>()
    val stater = _stateLiveData

    fun sendState(stateData: StateData) {
        stater.postValue(stateData)
    }


    fun sendStartState(){
        stater.postValue(StateData(LaunchStatus.START))
    }
    fun sendFailureState(throwable: Throwable){
        stater.postValue(StateData(LaunchStatus.FAILURE,throwable))
    }
    fun sendCompleteState(){
        stater.postValue(StateData(LaunchStatus.COMPLETE))
    }
}