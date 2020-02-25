package cn.ymex.kitx.widget.webview

import android.widget.TextView
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView

class BrowserChromeClient(
    private val progressView: BrowserView.Progressable? = null,
    private val textView: TextView? = null
) : WebChromeClient() {
    override fun onProgressChanged(webView: WebView, progress: Int) {
        super.onProgressChanged(webView, progress)
        progressView?.onProgressChanged(progress)
    }

    override fun onReceivedTitle(webView: WebView, title: String) {
        super.onReceivedTitle(webView, title)
        if (title.length > 8) {
            textView?.text = (title.substring(8)+"...")
        }else{
            textView?.text = title
        }
    }

}