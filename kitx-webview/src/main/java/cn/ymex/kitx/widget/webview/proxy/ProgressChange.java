package cn.ymex.kitx.widget.webview.proxy;

public interface ProgressChange {
        void onProgressChanged(int progress);

        void onStart();

        void onFinish();
    }