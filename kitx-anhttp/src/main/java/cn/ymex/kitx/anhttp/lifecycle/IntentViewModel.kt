package cn.ymex.kitx.anhttp.lifecycle

import android.os.Bundle
import androidx.lifecycle.ViewModel
import cn.ymex.kitx.core.lifecycle.MutableLifeData





/**
 * 路由通知
 */
open class IntentViewModel : ViewModel() {
    private val _intentRouter = MutableLifeData<IntentRouter>()
    val router = _intentRouter

    fun sendAction(intentRouter: IntentRouter) {
        router.postValue(intentRouter)
    }
}