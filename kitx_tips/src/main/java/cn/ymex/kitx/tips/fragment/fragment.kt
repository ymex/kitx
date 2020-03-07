package cn.ymex.kitx.tips.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import cn.ymex.kitx.tips.context.hideInputKeyBoard
import cn.ymex.kitx.tips.context.showInputKeyBoard
import cn.ymex.kitx.tips.view.find

/**
 * 查找组件
 */
fun <T : View> Fragment.find(@IdRes id: Int): T = view!!.find(id)

/**
 * 隐藏输入框
 */
fun Fragment.hideInputKeyBoard() {
    requireContext().hideInputKeyBoard(requireView())
}

/**
 * 显示输入框
 */
fun Fragment.showInputKeyBoard() {
    requireContext().showInputKeyBoard(requireView())
}


/**
 * 启动Activity
 */
inline fun <reified T : Activity> Fragment.startAction(
    bundle: Bundle = Bundle(),
    finish: Boolean = false,
    bundleKey: String = "ex_tips_bundle"
) {
    val intent = Intent(requireContext(), T::class.java)
    intent.putExtra(bundleKey, bundle)
    startActivity(intent)
    if (finish) {
        requireActivity().finish()
    }
}

/**
 * 启动Activity（startActivityForResult）
 */
inline fun <reified T : Activity> Fragment.startActionResult(
    bundle: Bundle = Bundle(),
    bundleKey: String = "ex_tips_bundle",
    requestCode: Int = 10086
) {
    val intent = Intent(requireContext(), T::class.java)
    intent.putExtra(bundleKey, bundle)
    startActivityForResult(intent, requestCode)
}


fun Fragment.getBundle(): Bundle {
    return arguments ?: Bundle()
}