package cn.ymex.kitx.anhttp.lifecycle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * 消息通知
 */
open class WarnViewModel : ViewModel() {
    private val _toastLiveData = MutableLiveData<String>()
    val toaster = _toastLiveData


    fun toast(message:String){
        toaster.postValue(message)
    }
}