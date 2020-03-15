package cn.ymex.kitx.tips.setting

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.M)
fun Context.startCanDrawOverlaysPermissionSettingActivity() {
    val packageURI =
        Uri.parse("package:" + this.packageName)
    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, packageURI)
    startActivity(intent)
}

@RequiresApi(api = Build.VERSION_CODES.O)
fun Context.startInstallPermissionSettingActivity() {
    val packageURI =
        Uri.parse("package:" + this.packageName)
    val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)
    startActivity(intent)
}


fun Context.canRequestPackageInstalls(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.packageManager.canRequestPackageInstalls()
    } else true
}


fun Context.canDrawOverlays(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Settings.canDrawOverlays(this)
    } else true
}

fun Context.notificationListenerEnable(): Boolean {
    var enable = false
    val packageName = this.packageName
    val flat = Settings.Secure.getString(
        this.contentResolver,
        "enabled_notification_listeners"
    )
    if (flat != null) {
        enable = flat.contains(packageName)
    }
    return enable
}


fun Context.startNotificationAccessSetting(): Boolean {
    try {
        val packageURI =
            Uri.parse("package:" + this.packageName)
        val intent =
            Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS", packageURI)
        startActivity(intent)
        return true
    } catch (e: ActivityNotFoundException) {
        try {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val cn = ComponentName(
                "com.android.settings",
                "com.android.settings.Settings\$NotificationAccessSettingsActivity"
            )
            intent.component = cn
            intent.putExtra(":settings:show_fragment", "NotificationAccessSettings")
            startActivity(intent)
            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
    return false
}