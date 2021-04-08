package cn.ymex.kitx.widget.label;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;

/**
 * ImageSpan
 */

public class ImageSpannable extends ImageSpan {

    public static final int ALIGN_FONTCENTER = 2;

    private int imgWidth, imgHeight;


    public ImageSpannable(Context context, int resourceId) {
        super(context, resourceId);
    }

    public ImageSpannable(Drawable drawable) {
        super(drawable);
    }

    public ImageSpannable(Drawable drawable, int verticalAlignment) {
        super(drawable,verticalAlignment);
    }

    public ImageSpannable(Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }

    public ImageSpannable(Context context, Bitmap b, int verticalAlignment){
        super(context, b, verticalAlignment);
    }


    /**
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        this.imgWidth = width;
        this.imgHeight = height;
    }

    @Override
    public Drawable getDrawable() {
        Drawable drawable = super.getDrawable();
        if (drawable != null && imgWidth > 0 && imgHeight > 0) {
            drawable.setBounds(0, 0, imgWidth, imgHeight);
        }
        return drawable;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     @NonNull Paint paint) {


        Drawable drawable = getDrawable();
        canvas.save();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();


        int transY = bottom - drawable.getBounds().bottom;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            transY -= fm.descent;
        } else if (mVerticalAlignment == ALIGN_FONTCENTER) {
            transY = ((y + fm.descent) + (y + fm.ascent)) / 2 - drawable.getBounds().bottom / 2;
        }

        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();
    }


    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        if (fm != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    }
}

