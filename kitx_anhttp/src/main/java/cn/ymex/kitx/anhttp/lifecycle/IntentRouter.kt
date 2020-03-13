package cn.ymex.kitx.anhttp.lifecycle

import android.os.Bundle
import androidx.lifecycle.MutableLiveData

/**
 * 需要页面跳转时使用
 */

data class IntentRouter(
    val path: String,
    val bundle: Bundle = Bundle(),
    val finish: Boolean = false
)
