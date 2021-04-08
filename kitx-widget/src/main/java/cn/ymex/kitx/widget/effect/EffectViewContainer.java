package cn.ymex.kitx.widget.effect;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author ymexc 2018/5/22
 * About:EffectView
 */
public class EffectViewContainer extends FrameLayout implements EffectView {

    private ViewDepute viewDepute;

    public EffectViewContainer(Context context) {
        this(context, null);
    }

    public EffectViewContainer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public EffectViewContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EffectViewContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        viewDepute = ViewDepute.instance();
        viewDepute.dealAttrs(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //getChildAt(0).setEnabled(false);
        this.setFocusable(true);
        viewDepute.onViewFinishInflate(this);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
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

    @Override
    public ViewDepute getViewDepute() {
        return viewDepute;
    }


    @Override
    public void dispatchSetEffect(View view, boolean flag) {
        viewDepute.dispatchSetEffect(view, flag);
    }
}
