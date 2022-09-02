package cn.ymex.kitx.widget.webview;

import android.net.Uri;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;


public class BrowserChromeClient extends WebChromeClient {
    private BrowserView.ProgressChange progressView;
    private TextView textView;

    public BrowserChromeClient() {
    }

    public BrowserChromeClient(TextView textView) {
        this.textView = textView;
    }

    public BrowserChromeClient(BrowserView.ProgressChange progressView, TextView textView) {
        this.progressView = progressView;
        this.textView = textView;
    }

    public BrowserChromeClient(BrowserView.ProgressChange progressView) {
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

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.e("JS-"+consoleMessage.messageLevel().name(),
                        consoleMessage.message() + "  " + consoleMessage.sourceId()+" -> ["+consoleMessage.lineNumber()+"] ");
        return true;//super.onConsoleMessage(consoleMessage);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }
}