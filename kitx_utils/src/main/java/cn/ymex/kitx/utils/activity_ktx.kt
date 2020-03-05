package cn.ymex.kitx.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes

fun <T : View> Activity.find(@IdRes id: Int): T = findViewById(id)

fun Activity.hideInputKeyBoard() {
    hideInputKeyBoard(this.window.peekDecorView())
}


fun Activity.showInputKeyBoard() {
    showInputKeyBoard(this.window.peekDecorView())
}




fun Activity.getBundle(): Bundle {
    return intent.getBundleExtra("ex_bundle") ?: Bundle()
}


inline fun <reified T : Activity> Activity.startAction(
    bundle: Bundle = Bundle(),
    finish: Boolean = false
) {
    val intent = Intent(this, T::class.java)
    intent.putExtra("ex_bundle", bundle)
    startActivity(intent)
    if (finish) {
        finish()
    }
}


inline fun <reified T : Activity> Activity.startActionResult(
    bundle: Bundle = Bundle(),
    requestCode: Int = 10086
) {
    val intent = Intent(this, T::class.java)
    intent.putExtra("ex_bundle", bundle)
    startActivityForResult(intent, requestCode)
}