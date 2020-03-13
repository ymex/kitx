package cn.ymex.kitx.anhttp.lifecycle

import androidx.lifecycle.MutableLiveData

/**
 * 页面状态通知
 */
open class StateViewModel : LifeViewModel() {
    private val _toastLiveData = MutableLiveData<String>()
    private val _stateLiveData = MutableLiveData<PageState>()
    private val _intentRouter = MutableLiveData<IntentRouter>()
    val router = _intentRouter
    val toaster = _toastLiveData
    val stater = _stateLiveData
}