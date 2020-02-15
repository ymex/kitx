package cn.ymex.kitx.widget.effect;

import android.graphics.drawable.Drawable;

/**
 *
 * @author ymexc 2018/5/26
 * About:surface
 */
public class ViewSurface {
    public boolean defSelector;
    /**
     * 背景
     */
    public Drawable bg;
    public int textColor;
    public Drawable selectedBg;
    public int selectedTextColor;
    public int strokeWidth;
    public int strokeColor;
    public int selectedStrokeColor;
    public Drawable image;
    public Drawable selectedImage;
    public int roundRadius;
    public int topLeftRadius;
    public int topRightRadius;
    public int bottomLeftRadius;
    public int bottomRightRadius;

    public boolean isRequestRoundRect() {
        if (roundRadius > 0 || topRightRadius > 0 || topLeftRadius > 0
                || bottomLeftRadius > 0 || bottomRightRadius > 0) {
            return true;
        }
        return false;
    }
}
