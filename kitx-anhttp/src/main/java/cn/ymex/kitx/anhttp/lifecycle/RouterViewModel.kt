package cn.ymex.kitx.anhttp.lifecycle

import androidx.lifecycle.MutableLiveData

/**
 * 路由通知
 */
open class RouterViewModel : LifeViewModel() {
    private val _intentRouter = MutableLiveData<IntentRouter>()
    val router = _intentRouter
}