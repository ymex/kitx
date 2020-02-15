package cn.ymex.kitx.widget.effect;

import android.view.View;


/**
 * @author ymexc 2018/5/22
 * About:缩放效果
 */
public class ScaleEffect implements Effect {

    private static final float DEFAULT_EFFECT_SCALE = .98f;
    private float scaleX = DEFAULT_EFFECT_SCALE;
    private float scaleY = DEFAULT_EFFECT_SCALE;
    private float toScaleX = 1.f;
    private float toScaleY = 1.f;


    @Override
    public void onStateChange(View view, ViewSurface surface, boolean pressed) {
        view.setScaleX(pressed ? scaleX : toScaleX);
        view.setScaleY(pressed ? scaleY : toScaleY);
    }

    public void setScaleX(float scaleX, float toScaleX) {
        this.scaleX = scaleX;
        this.toScaleX = toScaleX;
    }

    public void setScaleY(float scaleY, float toScaleY) {
        this.scaleY = scaleY;
        this.toScaleY = toScaleY;
    }
}
