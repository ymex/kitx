package cn.ymex.kitx.sample.webview

import android.R
import android.os.Bundle
import android.view.View
import cn.ymex.kitx.sample.databinding.ActivityBridgeBinding
import cn.ymex.kitx.start.app.ViewBindingActivity
import cn.ymex.kitx.widget.webview.BrowserChromeClient
import cn.ymex.kitx.widget.webview.BrowserClient
import cn.ymex.kitx.widget.webview.BrowserView
import cn.ymex.kitx.widget.webview.proxy.DefaultProgressBar
import cn.ymex.kitx.widget.webview.proxy.ProgressChange


class BridgeActivity : ViewBindingActivity<ActivityBridgeBinding>() {


    override fun viewBinding(): ActivityBridgeBinding {
        return ActivityBridgeBinding.inflate(layoutInflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = ActivityBridgeBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.vBrowser.run {
            webContentsDebuggingEnabled(true)
            BrowserView.setConsoleMessage(true)
            val progress: ProgressChange =
                DefaultProgressBar(context, null, R.attr.progressBarStyleHorizontal)
            setWebViewClient(object : BrowserClient(progress) {

            })
            setWebChromeClient(object : BrowserChromeClient(progress) {

            })
        }

//        val url = "file:///android_asset/js_bridge_test.html";
        val url = "https://www.baidu.com/";
        vb.vBrowser.loadUrl(url)
    }
}