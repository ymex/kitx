package cn.ymex.kitx.widget.effect;

import android.view.View;

/**
 * Created by ymexc on 2018/5/22.
 */
public interface Effect {
    void onStateChange(View view, ViewSurface surface, boolean pressed);
}
