package cn.ymex.kitx.widget.webview;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import cn.ymex.kitx.widget.webview.proxy.DefaultProgressBar;
import cn.ymex.kitx.widget.webview.proxy.ProgressChange;
import cn.ymex.kitx.widget.webview.proxy.ProxyWebView;

public class BrowserView extends FrameLayout {

    protected static boolean consoleMessage = false;


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

    private ProxyWebView mProxyWebView = new ProxyWebView(getContext());
    private ProgressChange mProgress = new DefaultProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);

    private TextView titleView = null;

    private void init() {
        addView(mProxyWebView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        if (mProgress instanceof View) {
            addView((View) mProgress, new LayoutParams(LayoutParams.MATCH_PARENT, dip2px(2.4F)));
        }
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        addView(linearLayout,layoutParams);


        initSetting(mProgress);
    }

//    private ImageView genIconImage(){
//        ImageView imageView = new ImageView(getContext());
//        ViewGroup.LayoutParams  layoutParams = new ViewGroup.LayoutParams(dip2px(22f),dip2px(22f));
//        imageView.setLayoutParams(layoutParams);
//        imageView.setImageResource();
//        return imageView;
//    }

    private void showDebugView() {

    }

    private void hideDebugView() {

    }

    private static int dip2px(float dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics()
        );
    }

    private void initSetting(ProgressChange progressChange) {
        getWebView().setWebViewClient(new BrowserClient(progressChange));
        getWebView().setWebChromeClient(new BrowserChromeClient(progressChange, titleView));
    }

    /**
     * inside webview
     */
    public ProxyWebView getWebView() {
        return mProxyWebView;
    }


    /**
     * webview progress
     */
    public ProgressChange getProgress() {
        return mProgress;
    }


    /**
     * set progress  for browser
     */
    public void setProgress(ProgressChange progress, LayoutParams params) {
        if (progress instanceof View) {
            if (mProgress instanceof View) {
                removeView((View) mProgress);
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
    public void loadHtml(String data) {
        mProxyWebView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }


    /**
     * load url
     */
    public void loadUrl(String url) {
        getWebView().loadUrl(url);
    }


    public void setWebViewClient(@NonNull BrowserClient client) {
        getWebView().setWebViewClient(client);
    }


    public void setWebChromeClient(@Nullable BrowserChromeClient client) {
        getWebView().setWebChromeClient(client);
    }


    public void destroyWebView() {
        mProxyWebView.destroy();
    }

    /**
     * 开启 chrome://inspect/#devices 调试
     */
    public void webContentsDebuggingEnabled(boolean enabled) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(enabled);
        }
    }

    /**
     * 打印 console log 信息
     *
     * @param consoleMessage 是否打印log
     */
    public static void setConsoleMessage(boolean consoleMessage) {
        BrowserView.consoleMessage = consoleMessage;
    }

}