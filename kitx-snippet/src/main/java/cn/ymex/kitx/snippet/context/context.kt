package cn.ymex.kitx.snippet.context

import android.Manifest
import android.app.*
import android.content.*
import android.content.pm.ApplicationInfo
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import cn.ymex.kitx.snippet.SNIPPET_EXTRA_NAME


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
    bundleKey: String = SNIPPET_EXTRA_NAME
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
    bundleKey: String = SNIPPET_EXTRA_NAME,
    requestCode: Int = 10086
) {
    val intent = Intent(this, T::class.java)
    intent.putExtra(bundleKey, bundle)
    requestActivity()?.startActivityForResult(intent, requestCode)
}


/**
 * 创建兼容Notification
 * Android.O 后创建 Notification必须指定 channel.
 */

fun Context.createNotificationCompat(
    channelId: String,
    channelName: String,
    builder: NotificationCompat.Builder
): Notification {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager =
            getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
    builder.setChannelId(channelId)
    return builder.build()
}

/**
 * 获取 NotificationManager
 */
fun Context.getNotificationManager(): NotificationManager {
    return getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
}

/**
 * 开启前台服务
 */
fun Context.startCompatForegroundService(intent: Intent) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else {
        startService(intent)
    }
}

/**
 * 获取 WindowManager
 */
fun Context.getWindowManager(): WindowManager {
    return getSystemService(Application.WINDOW_SERVICE) as WindowManager
}


@RequiresPermission(value = Manifest.permission.SYSTEM_ALERT_WINDOW)
fun Context.createFloatWindow(
    view: View,
    block: (layoutParams: WindowManager.LayoutParams) -> Unit
) {
    val wmParams = WindowManager.LayoutParams()
    //获取的是WindowManagerImpl.CompatModeWrapper
    val mWindowManager = getWindowManager()
    //设置window type
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
    } else {
        wmParams.type =
            WindowManager.LayoutParams.TYPE_PHONE or WindowManager.LayoutParams.TYPE_TOAST
    }

    //设置图片格式，效果为背景透明
    wmParams.format = PixelFormat.RGBA_8888
    //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
    wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    //调整悬浮窗显示的停靠位置为左侧置顶
    wmParams.gravity = Gravity.END or Gravity.BOTTOM
    // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
    wmParams.x = 0
    wmParams.y = 0

    //设置悬浮窗口长宽数据
    wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT
    wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT
    block(wmParams)
    mWindowManager.addView(view, wmParams)
}


val Context.versionCode:Long
    get() {
        val pm = packageManager.getPackageInfo(packageName,0)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            pm.longVersionCode
        } else {
            pm.versionCode.toLong()
        }
    }

val Context.versionName:String
    get()  = packageManager.getPackageInfo(packageName,0).versionName


fun Context.isMainProcess(): Boolean {
    val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val processInfo = am.runningAppProcesses
    val mainProcessName: String = packageName
    val myPid = Process.myPid()
    for (info in processInfo) {
        if (info.pid == myPid && mainProcessName == info.processName) {
            return true
        }
    }
    return false
}