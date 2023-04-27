package cn.ymex.kitx.snippet.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import cn.ymex.kitx.snippet.SNIPPET_EXTRA_NAME
import cn.ymex.kitx.snippet.context.hideInputKeyBoard
import cn.ymex.kitx.snippet.context.showInputKeyBoard


fun <T : View> Activity.find(@IdRes id: Int): T = findViewById(id)

fun Activity.hideInputKeyBoard(view: View?=null) {
    baseContext.hideInputKeyBoard(this.window.peekDecorView())
}


fun Activity.showInputKeyBoard() {
    baseContext.showInputKeyBoard(this.window.peekDecorView())
}


fun Activity.getBundle(bundleKey: String = SNIPPET_EXTRA_NAME): Bundle {
    return intent.getBundleExtra(bundleKey) ?: intent.extras ?: Bundle()
}

/**
 * 启动Activity
 */
inline fun <reified T : Activity> Activity.startAction(
    bundle: Bundle = Bundle(),
    finish: Boolean = false,
    bundleKey: String = SNIPPET_EXTRA_NAME
) {
    val intent = Intent(this, T::class.java)
    intent.putExtra(bundleKey, bundle)
    startActivity(intent)
    if (finish) {
        finish()
    }
}

/**
 * 启动Activity（startActivityForResult）
 */
inline fun <reified T : Activity> Activity.startActionResult(
    bundle: Bundle = Bundle(),
    bundleKey: String = SNIPPET_EXTRA_NAME,
    requestCode: Int = 10086
) {
    val intent = Intent(this, T::class.java)
    intent.putExtra(bundleKey, bundle)
    startActivityForResult(intent, requestCode)
}