package cn.ymex.kitx.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun <T : View> Fragment.find(@IdRes id: Int): T = view!!.find(id)


fun Fragment.hideInputKeyBoard() {
    context!!.hideInputKeyBoard(view!!)
}

fun Fragment.showInputKeyBoard() {
    context!!.showInputKeyBoard(view!!)
}