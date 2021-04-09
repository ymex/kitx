package cn.ymex.kitx.anhttp.lifecycle

import androidx.lifecycle.MutableLiveData

/**
 * 状态通知
 */
open class StateViewModel : LifeViewModel() {
    private val _stateLiveData = MutableLiveData<PageState>()
    val stater = _stateLiveData
}