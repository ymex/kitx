package cn.ymex.kitx.widget.webview.proxy;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Keep;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by du on 16/12/29.
 */

public class BridgeWebView extends WebView {

    private static final String LOG_TAG = "kitx_webview";

    private String bridgeScriptFile = "bridge.js";
    private String vConsoleScriptFile = "vconsole.js";

    private static boolean isDebug = false;

    private String APP_CACHE_DIRNAME;
    int callID = 0;
    private WebChromeClient webChromeClient;
    private WebViewClient webViewClient;
    private volatile boolean alertBoxBlock = true;



    private Handler mainHandler = new Handler(Looper.getMainLooper());
    Map<Integer, OnReturnValue> handlerMap = new HashMap<>();


    public BridgeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BridgeWebView(Context context) {
        super(context);
        init();
    }


    @Keep
    private void init() {
        APP_CACHE_DIRNAME = getContext().getFilesDir().getAbsolutePath() + "/webcache";
        WebSettings settings = getSettings();
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
            settings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setAllowFileAccess(false);
        settings.setAppCacheEnabled(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCachePath(APP_CACHE_DIRNAME);
        settings.setUseWideViewPort(true);
    }

    private String[] parseNamespace(String method) {
        int pos = method.lastIndexOf('.');
        String namespace = "";
        if (pos != -1) {
            namespace = method.substring(0, pos);
            method = method.substring(pos + 1);
        }
        return new String[]{namespace, method};
    }


    @Override
    public void clearCache(boolean includeDiskFiles) {
        super.clearCache(includeDiskFiles);
        CookieManager.getInstance().removeAllCookie();
        Context context = getContext();
        try {
            context.deleteDatabase("webview.db");
            context.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        File appCacheDir = new File(APP_CACHE_DIRNAME);
        File webviewCacheDir = new File(context.getCacheDir()
                .getAbsolutePath() + "/webviewCache");

        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }

        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }

    public void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e("Webview", "delete file no exists " + file.getAbsolutePath());
        }
    }


}
