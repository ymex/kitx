package cn.ymex.kitx.widget.webview.bridge;



public interface  CompletionHandler<T> {
    void complete(T retValue);
    void complete();
    void setProgressData(T value);
}
