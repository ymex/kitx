package cn.ymex.kitx.tips.setting

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationManagerCompat
import java.util.*


/**
 * 是否 忽略电池优化
 */

fun Context.ignoreBatteryOptimization(): Boolean {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isIgnoringBatteryOptimizations(this.packageName)
    }
    return true
}

/**
 * 打开 忽略电池优化设置
 */
@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("BatteryLife")
fun Context.startIgnoreBatteryOptimization() {
    val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
    intent.data = Uri.parse("package:" + this.packageName);
    startActivity(intent)
}


/**
 * 获取自启动管理页面的Intent
 * @param context context
 * @return 返回自启动管理页面的Intent
 */
fun Context.getAutoStartSettingIntent(): Intent {
    var componentName: ComponentName? = null
    val brand = Build.MANUFACTURER
    val intent = Intent()
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    when (brand.toLowerCase(Locale.ROOT)) {
        "samsung" ->
            componentName = ComponentName(
                "com.samsung.android.sm",
                "com.samsung.android.sm.app.dashboard.SmartManagerDashBoardActivity"
            )
        "huawei" ->
//            荣耀V8，EMUI 8.0.0，Android 8.0上，
//            需要com.huawei.permission.external_app_settings.USE_COMPONENT权限无法打开。
//            componentName = ComponentName(
//                "com.huawei.systemmanager",
//                "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"
//            )
            componentName = ComponentName(
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
            )
        "xiaomi" ->
            componentName = ComponentName(
                "com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity"
            )
        "vivo" ->  //            componentName = new ComponentName("com.iqoo.secure", "com.iqoo.secure.safaguard.PurviewTabActivity");
            componentName = ComponentName(
                "com.iqoo.secure",
                "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
            )
        "oppo" ->  //            componentName = new ComponentName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity");
            componentName = ComponentName(
                "com.coloros.oppoguardelf",
                "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity"
            )
        "yulong", "360" -> componentName = ComponentName(
            "com.yulong.android.coolsafe",
            "com.yulong.android.coolsafe.ui.activity.autorun.AutoRunListActivity"
        )
        "meizu" ->
            componentName =
                ComponentName("com.meizu.safe", "com.meizu.safe.permission.SmartBGActivity")
        "oneplus" -> componentName = ComponentName(
            "com.oneplus.security",
            "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"
        )
        "letv" -> {
            intent.action = "com.letv.android.permissionautoboot"
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.fromParts("package", packageName, null)
        }
        else -> {
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.fromParts("package", packageName, null)
        }
    }
    intent.component = componentName
    return intent
}


/**
 * 是否有安装权限
 */
fun Context.canInstallPackage(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        packageManager.canRequestPackageInstalls()
    } else true
}

/**
 * 是否有显示浮窗
 */
fun Context.canDrawOverlaysFloatWindow(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Settings.canDrawOverlays(this)
    } else true
}

/**
 * 是否有通知权限
 */


fun Context.areNotificationsEnabled(): Boolean {
    val notification: NotificationManagerCompat = NotificationManagerCompat.from(this)
    return notification.areNotificationsEnabled()
}


/**
 * 获取 通知权限设置页面的Intent
 */
@RequiresApi(Build.VERSION_CODES.M)
@RequiresPermission(value = Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
fun Context.getNotificationsSettingIntent(): Intent {
    val intent = Intent()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //5.0
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        intent.putExtra("app_package", packageName)
        intent.putExtra("app_uid", this.applicationInfo.uid)
        startActivity(intent)
    } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) { //4.4
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:$packageName")
    } else if (Build.VERSION.SDK_INT >= 15) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        intent.data = Uri.fromParts(
            "package",
            packageName,
            null
        )
    }

    return intent
}

/**
 * 获取 浮窗 设置页面的Intent
 */
@RequiresApi(api = Build.VERSION_CODES.M)
@RequiresPermission(value = Manifest.permission.SYSTEM_ALERT_WINDOW)
fun Context.getCanDrawOverlaysSettingIntent(): Intent {
    val packageURI =
        Uri.parse("package:$packageName")
    return Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, packageURI)
}

/**
 * 获取 安装apk 设置页面的Intent
 */
@RequiresApi(api = Build.VERSION_CODES.O)
@RequiresPermission(value = Manifest.permission.REQUEST_INSTALL_PACKAGES)
fun Context.getInstallPackageSettingIntent(): Intent {
    val packageURI =
        Uri.parse("package:$packageName")
    return Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI)

}