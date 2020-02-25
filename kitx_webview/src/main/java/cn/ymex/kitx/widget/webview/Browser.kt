package cn.ymex.kitx.widget.webview

import android.app.Application
import android.util.Log
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback

/**
 * 使用 x5 的应用只能打32位的包。
 * build.gradle 添加
 * ndk {abiFilters "armeabi", "armeabi-v7a", "x86", "mips"}
 *
 * x5 debug 页面：http://debugtbs.qq.com
 */
object Browser {

    fun create(application: Application) {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
        val cb: PreInitCallback = object : PreInitCallback {
            override fun onViewInitFinished(x5: Boolean) {
                Log.d("--app", " use browser x5 $x5")
            }

            override fun onCoreInitFinished() {
            }
        }
        //x5内核初始化接口
        QbSdk.setDownloadWithoutWifi(false)
        QbSdk.initX5Environment(application, cb)
    }
}