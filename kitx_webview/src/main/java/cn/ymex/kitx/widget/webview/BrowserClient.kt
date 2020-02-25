package cn.ymex.kitx.widget.webview

import android.graphics.Bitmap
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class BrowserClient(private val progressView: BrowserView.Progressable? = null) : WebViewClient() {
    override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
        webView.loadUrl(url)
        return true
    }

    override fun onPageStarted(view: WebView, url: String, logo: Bitmap?) {
        super.onPageStarted(view, url, logo)
        progressView?.onStart()
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        progressView?.onFinish()
    }


}