package cn.ymex.kitx.widget.webview;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class BrowserClient extends WebViewClient {

    private String LOG_TAG = "WEB_VIEW";

    private BrowserView.ProgressChange progressChange;
    private String erudaScriptFile = "eruda.min.js";

    public BrowserClient() {
    }

    public BrowserClient(BrowserView.ProgressChange progressChange) {
        this.progressChange = progressChange;
    }


    private WebResourceResponse interceptScriptResource(Context context, String url) {
        String sp = "/kitx/js/";
        if (!TextUtils.isEmpty(url) && url.contains(sp)) {
            try {
                if ((url.contains(sp + "eruda") || url.contains("eruda.mini.js"))) {
                    WebResourceResponse response = new WebResourceResponse("application/x-javascript",
                            "utf-8", context.getAssets().open(erudaScriptFile));
                    Log.i(LOG_TAG, "loaded bridge.js replace " + url);
                    return response;
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.i(LOG_TAG, "load local bridge js err ：" + e.toString());
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        WebResourceResponse response = interceptScriptResource(view.getContext(), request.getUrl().toString());
        if (response != null) {
            return response;
        }
        return super.shouldInterceptRequest(view, request);
    }


    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        WebResourceResponse response = interceptScriptResource(view.getContext(), url);
        if (response != null) {
            return response;
        }
        return super.shouldInterceptRequest(view, url);
    }



    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        webView.loadUrl(url);
        return true;
    }

    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        super.onPageStarted(webView, s, bitmap);
        if (progressChange != null) {
            progressChange.onStart();
        }
    }

    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
        if (progressChange != null) {
            progressChange.onFinish();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("",null);
        }
    }



    public String readFileFromAssets(Context context, String fileName) {
        if (null == context || null == fileName) return null;
        AssetManager am = context.getAssets();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream input = null;
        try {

            input = am.open(fileName);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            return new String(output.toByteArray(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}