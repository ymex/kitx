package cn.ymex.kitx.widget.webview.proxy;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import cn.ymex.kitx.widget.webview.BrowserView;

/**
 * 进度条
 */
public class DefaultProgressBar extends ProgressBar implements BrowserView.ProgressChange {
    public DefaultProgressBar(Context context) {
        super(context);
    }

    public DefaultProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DefaultProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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