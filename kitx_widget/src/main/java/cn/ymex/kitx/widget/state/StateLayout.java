package cn.ymex.kitx.widget.state;

import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.collection.ArrayMap;

import java.util.Map;


public interface StateLayout {
    int CONTENT_VIEW_LAYOUT = -0x20190811;

    int getCurrentLayoutId();

    void showContentView();

    void showView(@LayoutRes int layout);

    void showView(@LayoutRes int layout, StateLayout.Callback callBack);

    public FrameLayout getStateLayout();

    interface Callback {
        void onCreatedView(View view);
    }

    class Config {

        private Config() {
        }

        private static Config config = new Config();

        public static Config instanse() {
            if (config == null) {
                config = new Config();
            }
            return config;
        }

        private Map<Integer, Callback> callbackMap = new ArrayMap<>();

        /**
         * 在setContentView 前添加
         *
         * @param layout
         * @param callback
         * @return
         */
        public Config add(@LayoutRes int layout, Callback callback) {
            callbackMap.put(layout, callback);
            return this;
        }

        public Config remove(@LayoutRes int layout) {
            callbackMap.remove(layout);
            return this;
        }

        public Map<Integer, Callback> getConfig() {
            return callbackMap;
        }
    }
}
