package cn.ymex.kitx.sample

import android.content.Context
import androidx.multidex.MultiDex
import cn.ymex.kitx.anhttp.AnHttpManager
import cn.ymex.kitx.anhttp.anHttp
import cn.ymex.kitx.core.ApplicationContext

/**
 * Created by ymex on 2020/2/14.
 * About:
 */
class AppContext : ApplicationContext() {
    override fun onCreate() {
        super.onCreate()

        val retrofit =
            AnHttpManager.newRetrofitBuilder(baseUrl = "https://cn.bing.com/")
                .build()
        anHttp(retrofit)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
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