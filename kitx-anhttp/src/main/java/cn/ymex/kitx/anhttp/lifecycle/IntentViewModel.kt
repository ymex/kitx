package cn.ymex.kitx.anhttp.lifecycle

import android.os.Bundle
import androidx.lifecycle.ViewModel
import cn.ymex.kitx.core.lifecycle.MutableLifeData


/**
 * 需要页面跳转时使用
 */

data class IntentRouter(
    val path: String,
    val bundle: Bundle = Bundle(),
    val result: Boolean = false,
    val requestCode: Int = 0,
    val finish: Boolean = false,
)


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