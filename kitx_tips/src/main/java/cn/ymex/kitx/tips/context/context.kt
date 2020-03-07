package cn.ymex.kitx.tips.context

import android.app.Activity
import android.content.*
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes


fun Context.inflate(
    @LayoutRes layout: Int,
    root: ViewGroup? = null,
    attachToRoot: Boolean = false
): View = LayoutInflater.from(this).inflate(layout, root, attachToRoot)

/**
 * resource Id
 */
fun Context.resourceId(defType: String, name: String): Int {
    return resources.getIdentifier(name, defType, packageName)
}

/**
 * resource Id
 */
fun Context.resourceId(clazz: Class<Any>, name: String): Int {
    return resources.getIdentifier(name, clazz.simpleName, packageName)
}

/**
 * 隐藏软键盘
 */
fun Context.hideInputKeyBoard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(
        view.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}


/**
 * 显示键盘
 *
 * @param et 输入焦点
 */
fun Context.showInputKeyBoard(view: View) {
    view.isFocusable = true
    view.isFocusableInTouchMode = true
    view.requestFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}


/**
 * 复制文本到剪切板
 */
fun Context.copyTextToClipboard(text: String) {
    val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val mClipData = ClipData.newPlainText("text", text)
    cm.setPrimaryClip(mClipData)
}

/**
 * 获取剪贴板的文本
 *
 * @return 剪贴板的文本
 */
fun Context.getTextFromClipboard(): CharSequence? {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = clipboard.primaryClip
    return if (clip != null && clip.itemCount > 0) {
        clip.getItemAt(0).coerceToText(this)
    } else null
}

/**
 * 是否在调试环境
 */
fun Context.isDebugEnv(): Boolean {
    return try {
        val info = this.applicationInfo
        info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    } catch (e: Exception) {
        false
    }
}

/**
 * 从context 中获取 Activity
 */
fun Context.requestActivity(): Activity? {
    var self = this
    while (self is ContextWrapper) {
        if (self is Activity) {
            return self
        }
        self = self.baseContext
    }
    return null
}


/**
 * 启动Activity
 */
inline fun <reified T : Activity> Context.startAction(
    bundle: Bundle = Bundle(),
    finish: Boolean = false,
    bundleKey: String = "ex_tips_bundle"
) {
    val intent = Intent(this, T::class.java)
    intent.putExtra(bundleKey, bundle)
    startActivity(intent)
    if (finish) {
        requestActivity()?.finish()
    }
}

/**
 * 启动Activity（startActivityForResult）
 */
inline fun <reified T : Activity> Context.startActionResult(
    bundle: Bundle = Bundle(),
    bundleKey: String = "ex_tips_bundle",
    requestCode: Int = 10086
) {
    val intent = Intent(this, T::class.java)
    intent.putExtra(bundleKey, bundle)
    requestActivity()?.startActivityForResult(intent, requestCode)
}