package cn.ymex.kitx.widget.webview;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import cn.ymex.kitx.widget.webview.proxy.DefaultProgressBar;
import cn.ymex.kitx.widget.webview.proxy.ProgressChange;
import cn.ymex.kitx.widget.webview.proxy.ProxyWebView;

public class BrowserView extends FrameLayout {

    protected static boolean consoleMessage = false;
    private ProgressChange mProgressChange;
    private TextView mTitleView;
    private View mNavView;

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

    private final ProxyWebView mProxyWebView = new ProxyWebView(getContext());


    private void init() {
        addView(mProxyWebView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ProgressChange pv = new DefaultProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        setProgressView(pv, true);
        webNavigateBarEnabled(false);
        initSetting();
    }


    public void setProgressView(ProgressChange progressChange) {
        setProgressView(progressChange, false);
    }

    /**
     * @param progressView
     * @param attach       是否为视图加入到BrowserView中 。
     */
    public void setProgressView( ProgressChange progressView, Boolean attach) {
        if (this.mProgressChange != null && progressView instanceof View) {
            removeView((View) this.mProgressChange);
        }

        if (attach) {
            if (progressView instanceof View) {
                addView((View) progressView, new LayoutParams(LayoutParams.MATCH_PARENT, dip2px(2F)));
                ((View) progressView).setVisibility(GONE);
            }
        }
        this.mProgressChange = progressView;
        getWebView().setProgressView(progressView);
    }

    public void setTitleView(TextView titleView) {
        this.mTitleView = titleView;
        getWebView().setTitleView(titleView);
    }


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

    private void initSetting() {
        getWebView().setWebViewClient(new BrowserClient());
        getWebView().setWebChromeClient(new BrowserChromeClient());
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
        return mProgressChange;
    }




    /**
     * load html or file or url
     */
    public void loadHtml(String data) {
        getWebView().loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
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
        getWebView().destroy();
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
    public static void webConsoleMessage(boolean consoleMessage) {
        BrowserView.consoleMessage = consoleMessage;
    }

    /**
     * 开启操作导航条
     * @param enable
     */
    public void webNavigateBarEnabled(boolean enable) {
        if (enable) {
            if (mNavView == null) {
                mNavView = LayoutInflater.from(getContext()).inflate(R.layout.layout_webview_nav, null);
                LayoutParams layoutParams =  new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.BOTTOM;
                addView(mNavView,layoutParams);
                EditText editText = mNavView.findViewById(R.id.etUrl);
                getWebView().setUrlView(editText);
                editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_GO) {
                            getWebView().loadUrl(editText.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });
                ImageView ivRefresh = mNavView.findViewById(R.id.ivRefresh);
                ImageView ivForward = mNavView.findViewById(R.id.ivForward);
                ImageView ivBack = mNavView.findViewById(R.id.ivBack);
                ImageView ivDebug = mNavView.findViewById(R.id.ivDebug);
                ivRefresh.setOnClickListener(v -> getWebView().reload());
                ivForward.setOnClickListener(v -> getWebView().goForward());
                ivBack.setOnClickListener(v -> getWebView().goBack());
                ivDebug.setOnClickListener(v -> {
                    webContentsDebuggingEnabled(true);
                    Toast.makeText(getContext(), "已开启调试，使用 chrome://inspect/#devices ", Toast.LENGTH_LONG).show();
                });
            } else {
                mNavView.setVisibility(VISIBLE);
            }
        } else {
            if (mNavView != null) {
                mNavView.setVisibility(GONE);
            }
        }

    }
}