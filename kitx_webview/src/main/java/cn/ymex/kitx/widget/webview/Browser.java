package cn.ymex.kitx.widget.webview;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;


public class Browser {
    private static boolean BROWSER_DEBUG = false;

    /**
     * 是否是调试模式
     * @param flag
     */
    public static void debug(boolean flag) {
        BROWSER_DEBUG = flag;
    }


    public static boolean isDebug() {
        return BROWSER_DEBUG;
    }

    public static void create(Application application) {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。

        //x5内核初始化接口
        QbSdk.setDownloadWithoutWifi(false);
        QbSdk.initX5Environment(application, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                Log.d("--app", " use browser x5 $x5");
            }

            @Override
            public void onViewInitFinished(boolean b) {

            }
        });

    }
}
