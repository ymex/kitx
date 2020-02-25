package cn.ymex.kitx.widget.webview;


import android.graphics.Bitmap;

import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class BrowserClient extends WebViewClient {

    private BrowserView.Progressable progressable;

    public BrowserClient() {
    }

    public BrowserClient(BrowserView.Progressable progressable) {
        this.progressable = progressable;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        webView.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        super.onPageStarted(webView, s, bitmap);
        if (progressable != null) {
            progressable.onStart();
        }
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        if (progressable != null) {
            progressable.onFinish();
        }
    }

//    @Override
//    public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
//        System.out.println("------------shouldInterceptRequest)0:"+s);
//        return super.shouldInterceptRequest(webView, s);
//
//    }
}