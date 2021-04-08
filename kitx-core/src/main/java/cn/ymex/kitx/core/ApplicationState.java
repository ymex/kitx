package cn.ymex.kitx.core;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

/**
 * app 前后台状态
 */
final class ApplicationState implements LifecycleObserver {

    private Callback callback;
    private Context context;

    private ApplicationState(Context context, Callback callback) {
        this.callback = callback;
        this.context = context;
    }


    public static synchronized ApplicationState create(Context context, Callback callback) {
        if (!(context instanceof Application)) {
            throw new IllegalArgumentException("context just allow application");
        }
        return new ApplicationState(context, callback);
    }

    public void registApplicationState() {
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        if (callback != null) {
            callback.onAppEnterForeground(context);
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        if (callback != null) {
            callback.onAppEnterBackground(context);
        }

    }

    public interface Callback {
        /**
         * app enter Foreground
         */
        void onAppEnterForeground(Context context);

        /**
         * app enter background
         */
        void onAppEnterBackground(Context context);
    }
}
