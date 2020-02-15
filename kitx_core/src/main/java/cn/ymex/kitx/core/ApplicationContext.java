package cn.ymex.kitx.core;

import android.app.Application;
import android.content.Context;
import android.os.Process;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 扩展Application  增加对app 进入前后台的监听
 */
public class ApplicationContext extends Application implements ApplicationState.Callback {


    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println("-----m:"+ android.os.Process.myPid());
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

}
