package cn.ymex.kitx.tips.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import cn.ymex.kitx.tips.context.hideInputKeyBoard
import cn.ymex.kitx.tips.context.showInputKeyBoard

fun <T : View> Activity.find(@IdRes id: Int): T = findViewById(id)

fun Activity.hideInputKeyBoard() {
    baseContext.hideInputKeyBoard(this.window.peekDecorView())
}


fun Activity.showInputKeyBoard() {
    baseContext.showInputKeyBoard(this.window.peekDecorView())
}


fun Activity.getBundle(): Bundle {
    return intent.getBundleExtra("ex_tips_bundle") ?: intent.extras ?: Bundle()
}

/**
 * 启动Activity
 */
inline fun <reified T : Activity> Activity.startAction(
    bundle: Bundle = Bundle(),
    finish: Boolean = false
) {
    val intent = Intent(this, T::class.java)
    intent.putExtra("ex_tips_bundle", bundle)
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
    requestCode: Int = 10086
) {
    val intent = Intent(this, T::class.java)
    intent.putExtra("ex_bundle", bundle)
    startActivityForResult(intent, requestCode)
}