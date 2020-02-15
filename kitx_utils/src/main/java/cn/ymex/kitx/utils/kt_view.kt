package cn.ymex.kitx.utils

import android.view.View
import androidx.annotation.IdRes


fun <T : View> View.find(@IdRes id: Int): T = findViewById(id)


fun View.visibilityShow() {
    visibility = View.VISIBLE
}

fun View.visibilityHidden() {
    visibility = View.INVISIBLE
}


fun View.visibilityGone() {
    visibility = View.GONE
}


/**
 * 视图的尺寸
 */
fun View.size(block: (width: Int, height: Int) -> Unit) {
    post {
        block(width, height)
    }
}

/**
 * 防止过快点击（点击间隔800毫秒）
 */
fun View.setOnClickThrottleListener(timeOut: Long = 800, block: (view: View) -> Unit) {
    setOnClickListener(object : View.OnClickListener {
        private var preClickTime: Long = 0
        override fun onClick(v: View) {
            val now = System.currentTimeMillis()
            if (now - preClickTime <= timeOut) {
                return
            }
            block(v)
            preClickTime = now
        }
    })
}