package cn.ymex.kitx.widget.webview;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import cn.ymex.kitx.widget.webview.proxy.ProgressChange;


public class BrowserChromeClient extends WebChromeClient {
    private ProgressChange progressView;
    private TextView textView;
    public static int maxTitleTextLength = 12;


    public BrowserChromeClient() {
    }

    public BrowserChromeClient(TextView textView) {
        this.textView = textView;
    }

    public BrowserChromeClient(ProgressChange progressView, TextView textView) {
        this.progressView = progressView;
        this.textView = textView;
    }

    public BrowserChromeClient(ProgressChange progressView) {
        this.progressView = progressView;
    }

    @Override
    public void onProgressChanged(WebView webView, int progress) {
        super.onProgressChanged(webView, progress);
        if (progressView != null) {
            progressView.onProgressChanged(progress);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onReceivedTitle(WebView webView, String title) {
        super.onReceivedTitle(webView, title);
        if (title.length() > maxTitleTextLength) {
            if (textView != null) {
                textView.setText((title.substring(maxTitleTextLength) + "..."));
            }
        } else {
            if (textView != null) {
                textView.setText(title);
            }
        }
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        if (BrowserView.consoleMessage) {
            Log.e("JS-"+consoleMessage.messageLevel().name(),
                    consoleMessage.message() + "  " + consoleMessage.sourceId()+" -> ["+consoleMessage.lineNumber()+"] ");
        }
        return true;
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }
}