package cn.ymex.kitx.sample

import android.content.Context
import cn.ymex.kitx.core.ApplicationContext

/**
 * Created by ymex on 2020/2/14.
 * About:
 */
class AppContext : ApplicationContext() {
    override fun onCreate() {
        super.onCreate()
        println("---------m app:" + this)
    }

    override fun onAppEnterBackground(context: Context?) {
        super.onAppEnterBackground(context)
        println("---------------m onAppEnterBackground")
    }


    override fun onAppEnterForeground(context: Context?) {
        super.onAppEnterForeground(context)
        println("---------------m onAppEnterForeground")
    }
}