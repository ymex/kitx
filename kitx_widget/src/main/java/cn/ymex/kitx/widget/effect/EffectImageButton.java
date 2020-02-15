package cn.ymex.kitx.widget.effect;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

/**
 * Created by ymexc on 2018/5/26.
 * About:AppCompatImageButton
 */
public class EffectImageButton extends AppCompatImageButton {
    ViewDepute viewDepute;

    public EffectImageButton(Context context) {
        this(context, null);
    }

    public EffectImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EffectImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDepute = ViewDepute.instance();
        viewDepute.dealAttrs(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        viewDepute.onViewFinishInflate(this);
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        viewDepute.dispatchSetPressed(this, pressed);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        viewDepute.onFocusChanged(this, gainFocus, direction, previouslyFocusedRect);
    }

    public ViewDepute getViewDepute() {
        return viewDepute;
    }
}
