package cn.ymex.kitx.sample.permission

import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.annotation.RequiresApi

/**
 * Created by ymex on 2020/3/15.
 * About:
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class NoticeService : NotificationListenerService() {
    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
    }
}