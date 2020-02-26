package cn.ymex.kitx.anhttp.lifecycle

import android.os.Bundle
import androidx.lifecycle.MutableLiveData

data class IntentRouter(
    val path: String,
    val bundle: Bundle = Bundle(),
    val finish: Boolean = false
)

/**
 * 需要页面跳转时使用
 */
open class RouterViewModel : StateViewModel() {
    private val _intentRouter = MutableLiveData<IntentRouter>()
    val router = _intentRouter
}