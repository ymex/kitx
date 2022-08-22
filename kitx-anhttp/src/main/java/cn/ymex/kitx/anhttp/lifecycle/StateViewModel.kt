package cn.ymex.kitx.anhttp.lifecycle

import android.os.Bundle
import androidx.lifecycle.ViewModel
import cn.ymex.kitx.core.lifecycle.MutableLifeData



/**
 * 状态
 */
open class StateViewModel : ViewModel() {
    /**
     * 状态消息
     */
    private val _stateLiveData = MutableLifeData<StateData>()
    val stater = _stateLiveData

    fun sendState(stateData: StateData) {
        stater.postValue(stateData)
    }
}