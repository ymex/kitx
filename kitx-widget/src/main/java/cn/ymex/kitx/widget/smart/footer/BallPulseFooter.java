package cn.ymex.kitx.widget.smart.footer;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;

import cn.ymex.kitx.widget.R;
import cn.ymex.kitx.widget.smart.api.RefreshFooter;
import cn.ymex.kitx.widget.smart.api.RefreshLayout;
import cn.ymex.kitx.widget.smart.constant.SpinnerStyle;
import cn.ymex.kitx.widget.smart.internal.InternalAbstract;
import cn.ymex.kitx.widget.smart.util.SmartUtil;


/**
 * 球脉冲底部加载组件
 * Created by SCWANG on 2017/5/30.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class BallPulseFooter extends InternalAbstract implements RefreshFooter {

    //<editor-fold desc="属性变量">
//    public static final int DEFAULT_SIZE = 50; //dp

    protected boolean mManualNormalColor;
    protected boolean mManualAnimationColor;
//    protected SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;

    protected Paint mPaint;

    protected int mNormalColor = 0xffeeeeee;
    protected int mAnimatingColor = 0xffe75946;

    protected float mCircleSpacing;
//    protected float[] mScaleFloats = new float[]{1f, 1f, 1f};


    protected long mStartTime = 0;
    protected boolean mIsStarted = false;
    protected TimeInterpolator mInterpolator = new AccelerateDecelerateInterpolator();
//    protected List<ValueAnimator> mAnimators;
//    protected Map<ValueAnimator, ValueAnimator.AnimatorUpdateListener> mUpdateListeners = new HashMap<>();
    //</editor-fold>

    //<editor-fold desc="构造方法">
    public BallPulseFooter(Context context) {
        this(context, null);
    }

    public BallPulseFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);

        final View thisView = this;
        thisView.setMinimumHeight(SmartUtil.dp2px(60));

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BallPulseFooter);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        mSpinnerStyle = SpinnerStyle.Translate;
        mSpinnerStyle = SpinnerStyle.values[ta.getInt(R.styleable.BallPulseFooter_srlClassicsSpinnerStyle, mSpinnerStyle.ordinal)];

        if (ta.hasValue(R.styleable.BallPulseFooter_srlNormalColor)) {
            setNormalColor(ta.getColor(R.styleable.BallPulseFooter_srlNormalColor, 0));
        }
        if (ta.hasValue(R.styleable.BallPulseFooter_srlAnimatingColor)) {
            setAnimatingColor(ta.getColor(R.styleable.BallPulseFooter_srlAnimatingColor, 0));
        }

        ta.recycle();

        mCircleSpacing = SmartUtil.dp2px(4);

//        mAnimators = new ArrayList<>();
//        final int[] delays = new int[]{120, 240, 360};
//        for (int i = 0; i < 3; i++) {
//            final int index = i;
//
//            ValueAnimator animator = ValueAnimator.ofFloat(1, 0.3f, 1);
//
//            animator.setDuration(750);
//            animator.setRepeatCount(ValueAnimator.INFINITE);
//            animator.setTarget(i);
//            animator.setStartDelay(delays[i]);
//            animator.setInterpolator(null);
//
//            mUpdateListeners.put(animator, new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    mScaleFloats[index] = (float) animation.getAnimatedValue();
//                    thisView.postInvalidate();
//                }
//            });
//            mAnimators.add(animator);
//        }
    }

//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        if (mAnimators != null) for (int i = 0; i < mAnimators.size(); i++) {
//            mAnimators.get(i).cancel();
//            mAnimators.get(i).removeAllListeners();
//            mAnimators.get(i).removeAllUpdateListeners();
//        }
//    }

    //</editor-fold>

    @Override
    protected void dispatchDraw(Canvas canvas) {
        final View thisView = this;
        final int width = thisView.getWidth();
        final int height = thisView.getHeight();
        float radius = (Math.min(width, height) - mCircleSpacing * 2) / 6;
        float x = width / 2f - (radius * 2 + mCircleSpacing);
        float y = height / 2f;

        final long now = System.currentTimeMillis();

        for (int i = 0; i < 3; i++) {

            long time = now - mStartTime - 120 * (i + 1);
            float percent = time > 0 ? ((time%750)/750f) : 0;
            percent = mInterpolator.getInterpolation(percent);

            canvas.save();

            float translateX = x + (radius * 2) * i + mCircleSpacing * i;
            canvas.translate(translateX, y);

            if (percent < 0.5) {
                float scale = 1 - percent * 2 * 0.7f;
                canvas.scale(scale, scale);
            } else {
                float scale = percent * 2 * 0.7f - 0.4f;
                canvas.scale(scale, scale);
            }

            canvas.drawCircle(0, 0, radius, mPaint);
            canvas.restore();
        }

        super.dispatchDraw(canvas);

        if (mIsStarted) {
            thisView.invalidate();
        }
    }


    //<editor-fold desc="刷新方法 - RefreshFooter">

    @Override
    public void onStartAnimator(@NonNull RefreshLayout layout, int height, int maxDragHeight) {
        if (mIsStarted) return;

//        for (int i = 0; i < mAnimators.size(); i++) {
//            ValueAnimator animator = mAnimators.get(i);
//
//            //when the animator restart , add the updateListener again because they was removed by animator stop .
//            ValueAnimator.AnimatorUpdateListener updateListener = mUpdateListeners.get(animator);
//            if (updateListener != null) {
//                animator.addUpdateListener(updateListener);
//            }
//            animator.start();
//        }
        final View thisView = this;
        thisView.invalidate();
        mIsStarted = true;
        mStartTime = System.currentTimeMillis();
        mPaint.setColor(mAnimatingColor);
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
//        if (mAnimators != null && mIsStarted) {
//            mIsStarted = false;
//            mScaleFloats = new float[]{1f, 1f, 1f};
//            for (ValueAnimator animator : mAnimators) {
//                if (animator != null) {
//                    animator.removeAllUpdateListeners();
//                    animator.end();
//                }
//            }
//        }
        mIsStarted = false;
        mStartTime = 0;
        mPaint.setColor(mNormalColor);
        return 0;
    }

//    @Override
//    public boolean setNoMoreData(boolean noMoreData) {
//        return false;
//    }

    @Override@Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (!mManualAnimationColor && colors.length > 1) {
            setAnimatingColor(colors[0]);
            mManualAnimationColor = false;
        }
        if (!mManualNormalColor) {
            if (colors.length > 1) {
                setNormalColor(colors[1]);
            } else if (colors.length > 0) {
                setNormalColor(ColorUtils.compositeColors(0x99ffffff,colors[0]));
            }
            mManualNormalColor = false;
        }
    }

//    @NonNull
//    @Override
//    public SpinnerStyle getSpinnerStyle() {
//        return mSpinnerStyle;
//    }

    //</editor-fold>

    //<editor-fold desc="开放接口 - API">

    public BallPulseFooter setSpinnerStyle(SpinnerStyle mSpinnerStyle) {
        this.mSpinnerStyle = mSpinnerStyle;
        return this;
    }

    public BallPulseFooter setNormalColor(@ColorInt int color) {
        mNormalColor = color;
        mManualNormalColor = true;
        if (!mIsStarted) {
            mPaint.setColor(color);
        }
        return this;
    }

    public BallPulseFooter setAnimatingColor(@ColorInt int color) {
        mAnimatingColor = color;
        mManualAnimationColor = true;
        if (mIsStarted) {
            mPaint.setColor(color);
        }
        return this;
    }

    //</editor-fold>
}
