package cn.ymex.kitx.anhttp.lifecycle

import androidx.lifecycle.ViewModel
import cn.ymex.kitx.core.lifecycle.MutableLifeData

/**
 * 消息通知
 */
open class WarnViewModel : ViewModel() {
    private val _toastLiveData = MutableLifeData<String>()
    val toaster = _toastLiveData


    fun sendToast(message: String) {
        toaster.postValue(message)
    }
}