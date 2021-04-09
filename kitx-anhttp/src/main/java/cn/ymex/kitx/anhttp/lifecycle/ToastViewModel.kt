package cn.ymex.kitx.anhttp.lifecycle

import androidx.lifecycle.MutableLiveData

/**
 * 消息通知
 */
open class ToastViewModel : LifeViewModel() {
    private val _toastLiveData = MutableLiveData<String>()
    val toaster = _toastLiveData
}