package cn.ymex.kitx.tips.view

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import java.util.concurrent.atomic.AtomicInteger

/**
 * 查找组件
 */
fun <T : View> View.find(@IdRes id: Int): T = findViewById(id)

/**
 * 显示组件
 */
fun View.visibilityShow() {
    visibility = View.VISIBLE
}

/**
 * 显示组件隐藏组件（View.INVISIBLE）
 */
fun View.visibilityHidden() {
    visibility = View.INVISIBLE
}

/**
 * 显示组件隐藏组件（View.GONE）
 */
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

/**
 * 查找某一类型组件
 */
fun <T : View> View.findViews(
    cl: Class<T>,
    judge: (View, Class<T>) -> Boolean = { v, c -> v.javaClass.name == c.name }
): List<T> {
    val tvs = arrayListOf<T>()
    if (this is ViewGroup) {
        val cc = this.childCount
        for (i in 0 until cc) {
            tvs.addAll(getChildAt(i).findViews(cl))
        }
    }
    if (judge(this, cl)) {
        tvs.add(this as T)
    }
    return tvs
}