package cn.ymex.kitx.utils

import android.view.View
import androidx.annotation.IdRes
import java.util.concurrent.atomic.AtomicInteger


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


private val sNextGeneratedId: AtomicInteger = AtomicInteger(1)
/**
 * 动态生成id
 */
fun View.generateViewId(): Int {
    while (true) {
        val result: Int = sNextGeneratedId.get()
        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
        var newValue = result + 1
        if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
        if (sNextGeneratedId.compareAndSet(result, newValue)) {
            return result
        }
    }
}