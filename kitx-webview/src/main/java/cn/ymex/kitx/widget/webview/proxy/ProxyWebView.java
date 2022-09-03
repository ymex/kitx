package cn.ymex.kitx.widget.webview.proxy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

import cn.ymex.kitx.widget.webview.BrowserChromeClient;
import cn.ymex.kitx.widget.webview.BrowserClient;

public class ProxyWebView extends BridgeWebView {

    private ProgressChange progressView;
    private TextView titleView;
    private TextView urlView;

    public ProxyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProxyWebView(Context context) {
        super(context);
        init();
    }


    public void init() {
        setBackgroundColor(85621);
        initSetting();
    }

    @Override
    public void loadUrl(@NonNull String url) {
        super.loadUrl(url);
        setUrlViewText(url);
    }

    @Override
    public void loadUrl(@NonNull String url, @NonNull Map<String, String> additionalHttpHeaders) {
        super.loadUrl(url, additionalHttpHeaders);
        setUrlViewText(url);
    }


    @Override
    public void setWebViewClient(@NonNull WebViewClient client) {
        super.setWebViewClient(client);
        if (!(client instanceof BrowserClient)) {
            throw new IllegalArgumentException(client.getClass().getName() + "need extends BrowserClient.");
        }
    }

    @Override
    public void setWebChromeClient(@Nullable WebChromeClient client) {
        super.setWebChromeClient(client);
        if (!(client instanceof BrowserChromeClient)) {
            throw new IllegalArgumentException(client.getClass().getName() + "need extends BrowserChromeClient.");
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initSetting() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);

        webSetting.setSupportMultipleWindows(true);
//        webSetting.loadWithOverviewMode = true
        webSetting.setAppCacheEnabled(true);
//        webSetting.databaseEnabled = true


        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setPluginState(WebSettings.PluginState.ON);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);

        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension

        // Enable remote debugging via chrome://inspect
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                setWebContentsDebuggingEnabled(true);
//            }

        // Allow use of Local Storage
        webSetting.setDomStorageEnabled(true);
        // AppRTC requires third party cookies to work
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(this, true);
        }

        //允许 http ,https 混合模式请求图片，manifest.xml 添加：android:usesCleartextTraffic="true"
        webSetting.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //播放视频时，可自动播放
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSetting.setMediaPlaybackRequiresUserGesture(false);
        }
        // WebView UA
        webSetting.setUserAgentString(webSetting.getUserAgentString() + " app/kitx(webivew) ");

    }


    public void setProgressView(ProgressChange progressView) {
        this.progressView = progressView;
    }

    public void setTitleView(TextView titleView) {
        this.titleView = titleView;
    }

    public void setUrlView(TextView urlView) {
        this.urlView = urlView;
    }

    public void setUrlViewText(String url) {
        if (urlView != null){
            urlView.setText(url);
        }
    }

    public TextView getTitleView() {
        return titleView;
    }

    public ProgressChange getProgressView() {
        return progressView;
    }


}