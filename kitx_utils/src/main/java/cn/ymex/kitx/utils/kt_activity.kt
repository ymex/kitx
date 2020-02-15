package cn.ymex.kitx.utils

import android.app.Activity
import android.content.Context
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