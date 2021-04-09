package cn.ymex.kitx.anhttp.lifecycle

import androidx.lifecycle.MutableLiveData

/**
 * 通知
 */
open class ActionViewModel : StateViewModel() {

    private val _intentRouter = MutableLiveData<IntentRouter>()
    val router = _intentRouter


    private val _toastLiveData = MutableLiveData<String>()
    val toaster = _toastLiveData


    fun startAction(intentRouter: IntentRouter){
        router.postValue(intentRouter)
    }

    fun toast(message:String){
        toaster.postValue(message)
    }
}