package cn.ymex.kitx.core;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 扩展Application  增加对app 进入前后台的监听
 */
public class ApplicationContext extends Application implements ApplicationState.Callback, ViewModelStoreOwner {

    private ViewModelStore vmStore;

    @Override
    public void onCreate() {
        super.onCreate();
        if (vmStore == null) {
            vmStore = new ViewModelStore();
        }
        create(this, this);
    }

    @Override
    public void onAppEnterForeground(Context context) {

    }

    @Override
    public void onAppEnterBackground(Context context) {

    }

    private static AtomicBoolean inited = new AtomicBoolean(false);

    public static void create(Application app, ApplicationState.Callback callback) {
        if (!inited.get()) {
            inited.set(true);
            Kitx.init(app);
            ActivityManager.get().registerActivityLifecycleCallbacks(app);
            ApplicationState.create(app, callback).registApplicationState();
        }
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return vmStore;
    }
}
