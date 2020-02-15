package cn.ymex.kitx.sample.core

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by ymex on 2020/2/14.
 * About:
 */
class MutService :Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
}