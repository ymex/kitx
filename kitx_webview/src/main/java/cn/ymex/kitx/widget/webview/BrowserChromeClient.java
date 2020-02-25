package cn.ymex.kitx.widget.webview;

import android.widget.TextView;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

public class BrowserChromeClient extends WebChromeClient {
    private BrowserView.Progressable progressView;
    private TextView textView;

    public BrowserChromeClient() {
    }

    public BrowserChromeClient(TextView textView) {
        this.textView = textView;
    }

    public BrowserChromeClient(BrowserView.Progressable progressView, TextView textView) {
        this.progressView = progressView;
        this.textView = textView;
    }

    public BrowserChromeClient(BrowserView.Progressable progressView) {
        this.progressView = progressView;
    }

    @Override
    public void onProgressChanged(WebView webView, int progress) {
        super.onProgressChanged(webView, progress);
        if (progressView != null) {
            progressView.onProgressChanged(progress);
        }
    }

    @Override
    public void onReceivedTitle(WebView webView, String title) {
        super.onReceivedTitle(webView, title);
        if (title.length() > 8) {
            if (textView != null) {
                textView.setText((title.substring(8) + "..."));
            }
        } else {
            if (textView != null) {
                textView.setText(title);
            }
        }
    }
}