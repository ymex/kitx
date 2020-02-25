package cn.ymex.kitx.sample.webview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.ymex.kitx.sample.R
import cn.ymex.kitx.widget.webview.bridge.BridgeWebView
import cn.ymex.kitx.widget.webview.bridge.OnReturnValue
import kotlinx.android.synthetic.main.activity_bridge.*


class BridgeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bridge)
        // set debug mode
        BridgeWebView.setWebContentsDebuggingEnabled(true)
        vBrowser.webView.setJavascriptObject(JsCallApi())
        vBrowser.loadUrl("file:///android_asset/js_bridge_test.html")


        vBrowser.webView.run {
            callHandler(
                "addValue",
                arrayOf<Any>(1, 6),
                OnReturnValue<Int> {
                    Toast.makeText(context, "result:$it", Toast.LENGTH_SHORT).show()
                })

            callHandler(
                "append",
                arrayOf<Any>("Hello", " ", "MoMo"),
                OnReturnValue<String> {
                    Toast.makeText(context, "result:$it", Toast.LENGTH_SHORT).show()
                })
        }
    }
}