package cn.ymex.kitx.sample.webview

import android.os.CountDownTimer
import android.webkit.JavascriptInterface
import cn.ymex.kitx.widget.webview.bridge.CompletionHandler

class JsCallApi {
    /**
     * 同步方法，
     *
     *
     */
    @JavascriptInterface
    fun hiSyn(msg: Any): String {
        return "java return : $msg"
    }

    /**
     * 异步方法
     */
    @JavascriptInterface
    fun hiAsyn(
        msg: Any,
        handler: CompletionHandler<String>
    ) {
        handler.complete("java return : $msg")
    }

    @JavascriptInterface
    fun callProgress(
        args: Any?,
        handler: CompletionHandler<Int?>
    ) {
        object : CountDownTimer(11000, 1000) {
            var i = 10
            override fun onTick(millisUntilFinished: Long) { //setProgressData can be called many times util complete be called.
                handler.setProgressData(i--)
            }

            override fun onFinish() { //complete the js invocation with data; handler will be invalid when complete is called
                handler.complete(0)
            }
        }.start()
    }
}