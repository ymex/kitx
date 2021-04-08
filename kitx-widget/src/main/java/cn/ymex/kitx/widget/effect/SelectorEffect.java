package cn.ymex.kitx.widget.effect;

import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 *
 * @author ymex 2018/5/22
 * About: selector
 */
public class SelectorEffect implements Effect {
    @Override
    public void onStateChange(View view, ViewSurface surface, boolean pressed) {
        if (surface.defSelector) {
            view.setPressed(pressed);
            return;
        }
        if (surface.selectedBg != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(pressed ? surface.selectedBg : surface.bg);
            } else {
                view.setBackgroundDrawable(pressed ? surface.selectedBg : surface.bg);
            }
        }
        if (surface.selectedTextColor != 0 && view instanceof TextView) {
            ((TextView) view).setTextColor(pressed ? surface.selectedTextColor : surface.textColor);
        }

        if (surface.selectedImage != null && view instanceof ImageView && surface.image != null) {
            ((ImageView) view).setImageDrawable(pressed ? surface.selectedImage : surface.image);
        }
    }
}
