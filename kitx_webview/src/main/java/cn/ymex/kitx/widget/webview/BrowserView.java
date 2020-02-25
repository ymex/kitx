package cn.ymex.kitx.widget.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebSettings;

import cn.ymex.kitx.widget.webview.bridge.BridgeWebView;

public class BrowserView extends FrameLayout {

    public BrowserView(@NonNull Context context) {
        super(context);
        init();
    }

    public BrowserView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BrowserView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BrowserView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private WebView mWebView = new WebView(getContext());
    private Progressable mProgress = new DefalutProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);

    private TextView titleView = null;

    private void init() {
        addView(mWebView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        if (mProgress instanceof View) {
            addView((View) mProgress, new LayoutParams(LayoutParams.MATCH_PARENT, dip2px(2.4F)));
        }
        initSetting(mProgress);
    }

    private int dip2px(float dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics()
        );
    }

    private void initSetting(Progressable progressable) {
        mWebView.setWebViewClient(new BrowserClient(progressable));
        mWebView.setWebChromeClient(new BrowserChromeClient(progressable, titleView));

    }

    /**
     * inside webview
     */
    public WebView getWebView() {
        return mWebView;
    }


    /**
     * webview progress
     */
    public Progressable getProgress() {
        return mProgress;
    }


    /**
     * set progress  for browser
     */
    public void setProgress(Progressable progress, LayoutParams params) {
        if (progress instanceof View){
            if (mProgress instanceof View){
                removeView((View)mProgress);
            }
            if (params != null) {
                addView((View) progress, params);
            } else {
                addView((View) progress);
            }
        }

        mProgress = progress;
    }

    /**
     * load html or file or url
     */
    public void loadData(String data) {
        if (data.startsWith("http://") || data.startsWith("https://")) {
            mWebView.loadUrl(data);
        } else if (data.startsWith("file://")) {
            mWebView.loadUrl(data);
        } else {
            mWebView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
        }
    }


    /**
     * load url
     */
    public void  loadUrl(String url) {
        getWebView().loadUrl(url);
    }


    public interface Progressable {
        void onProgressChanged(int progress);

        void onStart();

        void onFinish();
    }


    public static class WebView extends BridgeWebView {
        public WebView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public WebView(Context context) {
            super(context);
            init();
        }


        public void init() {
            setBackgroundColor(85621);
            initSetting();
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setWebContentsDebuggingEnabled(true);
            }

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
            webSetting.setUserAgent(webSetting.getUserAgentString() + " app/kitx(webivew) ");

        }

        @Override
        protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
            boolean ret = super.drawChild(canvas, child, drawingTime);
            canvas.save();
            Paint paint = new Paint();
            paint.setColor(0x7fff0000);
            paint.setTextSize(24.f);
            paint.setAntiAlias(true);
            if (getX5WebViewExtension() != null) {
                canvas.drawText(this.getContext().getPackageName() + "-pid:"
                        + android.os.Process.myPid(), 10, 50, paint);
                canvas.drawText(
                        "X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
                        100, paint);
            } else {
                canvas.drawText(this.getContext().getPackageName() + "-pid:"
                        + android.os.Process.myPid(), 10, 50, paint);
                canvas.drawText("Sys Core", 10, 100, paint);
            }
            canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
            canvas.drawText(Build.MODEL, 10, 200, paint);
            canvas.restore();
            return ret;
        }
    }

    /**
     * 进度条
     */
    static class DefalutProgressBar extends ProgressBar implements Progressable {
        public DefalutProgressBar(Context context) {
            super(context);
        }

        public DefalutProgressBar(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public DefalutProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public DefalutProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onProgressChanged(int progress) {
            setProgress(progress);
        }

        @Override
        public void onStart() {
            setVisibility(VISIBLE);
        }

        @Override
        public void onFinish() {
            setVisibility(GONE);
        }

    }
}