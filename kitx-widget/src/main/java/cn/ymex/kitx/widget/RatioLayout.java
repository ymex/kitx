package cn.ymex.kitx.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

/**
 * 比例布局
 */
public class RatioLayout extends RelativeLayout {

    float ratioW = 1, ratioH = 1;
    int datum = 1;

    public RatioLayout(Context context) {
        this(context, null);
    }

    public RatioLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadFromAttributes(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        loadFromAttributes(context, attrs);
    }

    private void loadFromAttributes(Context context, AttributeSet attrs) {
        final TypedArray array =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.RatioLayout, 0, 0);
        ratioW = array.getFloat(R.styleable.RatioLayout_widthRatio, 1);
        ratioH = array.getFloat(R.styleable.RatioLayout_heightRatio, 1);
        datum = array.getInt(R.styleable.RatioLayout_datumRatio, 1);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);


        if (datum == 1) {//基于宽
            int newH = (int) (w * (ratioH / ratioW));
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(newH, MeasureSpec.EXACTLY));
        } else {
            int newW = (int) (h * (ratioW / ratioH));
            super.onMeasure(MeasureSpec.makeMeasureSpec(newW, MeasureSpec.EXACTLY), heightMeasureSpec);
        }


    }
}
