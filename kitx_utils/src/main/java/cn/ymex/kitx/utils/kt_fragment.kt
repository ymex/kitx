package cn.ymex.kitx.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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


inline fun <reified T : Activity> Fragment.startAction(
    bundle: Bundle = Bundle(),
    finish: Boolean = false
) {

    val intent = Intent(requireActivity(), T::class.java)
    intent.putExtra("ex_bundle", bundle)
    startActivity(intent)
    if (finish) {
        requireActivity().finish()
    }
}


inline fun <reified T : Activity> Fragment.startActionResult(
    bundle: Bundle = Bundle(),
    requestCode: Int = 10086
) {
    val intent = Intent(requireActivity(), T::class.java)
    intent.putExtra("ex_bundle", bundle)
    startActivityForResult(intent, requestCode)
}

