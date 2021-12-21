package cn.ymex.kitx.snippet.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import cn.ymex.kitx.snippet.SNIPPET_EXTRA_NAME
import cn.ymex.kitx.snippet.context.hideInputKeyBoard
import cn.ymex.kitx.snippet.context.showInputKeyBoard
import cn.ymex.kitx.snippet.view.find

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
    bundleKey: String = SNIPPET_EXTRA_NAME
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
    bundleKey: String = SNIPPET_EXTRA_NAME,
    requestCode: Int = 10086
) {
    val intent = Intent(requireContext(), T::class.java)
    intent.putExtra(bundleKey, bundle)
    startActivityForResult(intent, requestCode)
}


fun Fragment.getBundle(): Bundle {
    return arguments ?: Bundle()
}