package cn.ymex.kitx.widget.webview;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by ymex on 2020/2/25.
 * About:
 */
public class Browser {
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
