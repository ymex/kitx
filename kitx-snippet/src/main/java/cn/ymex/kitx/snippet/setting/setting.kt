package cn.ymex.kitx.snippet.setting

import android.Manifest
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationManagerCompat
import java.lang.reflect.Method
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
 * notification 监听权限 是否打开。
 */
fun Context.areNotificationListenerEnable(): Boolean {
//    var enable = false
//    val packageName = this.packageName
//    val flat = Settings.Secure.getString(
//        this.contentResolver,
//        "enabled_notification_listeners"
//    )
//    if (flat != null) {
//        enable = flat.contains(packageName)
//    }
//    return enable

    val packageNames = NotificationManagerCompat.getEnabledListenerPackages(this);
    if (packageNames.contains(packageName)) {
        return true
    }
    return false

}


@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
@RequiresPermission(value = Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE)
fun Context.getNotificationListenerAccessSettingIntent(): Intent {
    val packageURI =
        Uri.parse("package:" + this.packageName)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
        val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS, packageURI)
        if (packageManager.resolveActivity(intent, 0) != null) {
            return intent
        }
    }

    val intent = Intent()
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    val cn = ComponentName(
        "com.android.settings",
        "com.android.settings.Settings\$NotificationAccessSettingsActivity"
    )
    intent.component = cn
    intent.putExtra(":settings:show_fragment", "NotificationAccessSettings")
    return intent
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

/**
 *fix 应用进程被杀后再次启动时，服务不生效.
 * 使用， 在启动服务前调用。
 */
fun Context.toggleNotificationListenerService(serviceClazz: Class<*>) {
    val pm: PackageManager = packageManager
    pm.setComponentEnabledSetting(
        ComponentName(
            this,
            serviceClazz
        ),
        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
    )
    pm.setComponentEnabledSetting(
        ComponentName(
            this,
            serviceClazz
        ),
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
    )
}

/**
 * 本应用注册的服务是否存在 并运行
 */
@Suppress("DEPRECATION")
fun Context.isServiceExisted(className: String): Boolean {
    val activityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    //getRunningServices和  getRunningAppProcesses() 在 Android O 以上版本只能获取应用本身的服务和进程
    val serviceInfos = activityManager.getRunningServices(Int.MAX_VALUE)
    if (serviceInfos.size <= 0) {
        return false
    }
    for (i in 0 until serviceInfos.size) {
        val serviceName = serviceInfos[i].service
        if (serviceName.className == className) {
            return true
        }
    }
    return false
}

//已测MIUI10/MIUI11/MIU13可用
fun Context.isMuiAllowAutoStart(): Boolean {
    try {
        @SuppressLint("PrivateApi")
        val method: Method = Class.forName("android.miui.AppOpsUtils")
            .getMethod("getApplicationAutoStart", Context::class.java, String::class.java)
        return method.invoke(null, this, this.packageName) as Int == 0 //0已允许, 1已拒绝
    } catch (e: Exception) {
        e.printStackTrace()
    }
    //如果系统更新改了api可能导致没法判断
    return false
}


