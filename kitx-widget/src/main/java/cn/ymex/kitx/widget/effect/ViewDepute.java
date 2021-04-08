package cn.ymex.kitx.widget.effect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import cn.ymex.kitx.widget.R;

/**
 *
 * @author ymexc 2018/5/26
 * About:代理
 */
public class ViewDepute {
    private Effect effect;
    private ViewSurface surface;
    public static final int EFFECT_MODEL_PRESS = 1;
    public static final int EFFECT_MODEL_FOCUS = 2;
    public static final int EFFECT_MODEL_BOTH = 3;
    public static final int EFFECT_MODEL_MANUAL = 4;

    private int effectModel = EFFECT_MODEL_BOTH;

    private ViewDepute() {
        super();
        this.surface = new ViewSurface();
    }

    public static ViewDepute instance() {
        return new ViewDepute();
    }

    public void dealAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EffectViewContainer);
        surface.defSelector = typedArray.getBoolean(R.styleable.EffectViewContainer_effect_xml_selector, false);
        surface.roundRadius = typedArray.getDimensionPixelSize(R.styleable.EffectViewContainer_effect_round_radius, 0);
        surface.topLeftRadius = typedArray.getDimensionPixelSize(R.styleable.EffectViewContainer_effect_top_left_radius, 0);
        surface.topRightRadius = typedArray.getDimensionPixelSize(R.styleable.EffectViewContainer_effect_top_right_radius, 0);
        surface.bottomLeftRadius = typedArray.getDimensionPixelSize(R.styleable.EffectViewContainer_effect_bottom_left_radius, 0);
        surface.bottomRightRadius = typedArray.getDimensionPixelSize(R.styleable.EffectViewContainer_effect_bottom_right_radius, 0);
        surface.selectedBg = typedArray.getDrawable(R.styleable.EffectViewContainer_effect_selected_background);
        surface.selectedTextColor = typedArray.getColor(R.styleable.EffectViewContainer_effect_selected_text_color, 0);
        surface.strokeColor = typedArray.getColor(R.styleable.EffectViewContainer_effect_stroke_color, 0);
        surface.selectedStrokeColor = typedArray.getColor(R.styleable.EffectViewContainer_effect_selected_stroke_color, 0);
        surface.strokeWidth = typedArray.getDimensionPixelSize(R.styleable.EffectViewContainer_effect_stroke_width, 0);
        surface.selectedImage = typedArray.getDrawable(R.styleable.EffectViewContainer_effect_selected_image);
        if (surface.strokeWidth > 0) {
            if (surface.strokeColor != 0) {
                surface.selectedStrokeColor = surface.selectedStrokeColor != 0 ? surface.selectedStrokeColor : surface.strokeColor;
            }
        }
        setEffectModel(typedArray.getInt(R.styleable.EffectViewContainer_effect_model, EFFECT_MODEL_BOTH));
        String effectC = typedArray.getString(R.styleable.EffectViewContainer_effect);
        if (!TextUtils.isEmpty(effectC)) {
            try {
                Class c = Class.forName(effectC);
                Object o = c.newInstance();
                if (o instanceof Effect) {
                    this.effect = (Effect) o;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        typedArray.recycle();
    }

    public void onViewFinishInflate(View proView) {
        proView.setClickable(true);
        View view;
        if (proView instanceof ViewGroup) {

            int childCount = ((ViewGroup) proView).getChildCount();
            if (childCount > 1) {
                throw new RuntimeException("just allow single view!");
            }
            view = ((ViewGroup) proView).getChildAt(0);
            if (view instanceof Button || view instanceof ImageButton) {
                view.setFocusable(false);
            }
        } else {
            proView.setFocusable(true);
            view = proView;
        }
        if (view == null) {
            return;
        }
        surface.bg = view.getBackground();
        setRoundRect(view);
        if (view instanceof TextView) {
            surface.textColor = ((TextView) view).getCurrentTextColor();
        }
        if (view instanceof ImageView) {
            surface.image = ((ImageView) view).getDrawable();
        }
        if (effect != null) {
            return;
        }

        if (surface.defSelector) {
            this.effect = new SelectorEffect();
        } else if (surface.selectedBg != null || surface.selectedTextColor != 0 || surface.selectedImage != null) {
            this.effect = new SelectorEffect();
        } else {
            this.effect = new AlphaEffect();
        }
    }

    private void setRoundRect(View view) {

        if (surface.bg != null && surface.bg instanceof ColorDrawable) {
            int bgColor = ((ColorDrawable) surface.bg).getColor();
            surface.bg = createRoundRectDrawable(bgColor, surface.strokeWidth, surface.strokeColor);
            if (!surface.defSelector) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(surface.bg);
                } else {
                    view.setBackgroundDrawable(surface.bg);
                }
            }

        }
        if (surface.selectedBg != null && surface.selectedBg instanceof ColorDrawable) {
            surface.selectedBg = createRoundRectDrawable(((ColorDrawable) surface.selectedBg).getColor(), surface.strokeWidth, surface.selectedStrokeColor);
        }
    }

    /**
     * int color : background color
     * return Drawable
     */
    private Drawable createRoundRectDrawable(int bgcolor, int strokeWidth, int strokeColor) {

        float[] outerR = new float[]{
                surface.topLeftRadius, surface.topLeftRadius,
                surface.topRightRadius, surface.topRightRadius,
                surface.bottomRightRadius, surface.bottomRightRadius,
                surface.bottomLeftRadius, surface.bottomLeftRadius
        };
        if (surface.roundRadius > 0) {
            outerR = new float[]{
                    surface.roundRadius, surface.roundRadius,
                    surface.roundRadius, surface.roundRadius,
                    surface.roundRadius, surface.roundRadius,
                    surface.roundRadius, surface.roundRadius
            };
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(bgcolor);
        gradientDrawable.setCornerRadii(outerR);
        if (strokeWidth > 0 && strokeColor != 0) {
            gradientDrawable.setStroke(strokeWidth, strokeColor);
        }
        return gradientDrawable;
    }


    public ViewSurface getSurface() {
        return surface;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public void dispatchSetPressed(View view, boolean pressed) {
        if (effectModel == EFFECT_MODEL_BOTH || effectModel == EFFECT_MODEL_PRESS) {
            effect(view, pressed, -1, null);
        }
    }

    private void effect(View view, boolean flag, int direction, @Nullable Rect previouslyFocusedRect) {
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = ((ViewGroup) view).getChildAt(i);
                this.effect.onStateChange(childView, surface, flag);
            }
        } else {
            effect.onStateChange(view, surface, flag);
        }
    }


    public void onFocusChanged(View view, boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        if (effectModel == EFFECT_MODEL_BOTH || effectModel == EFFECT_MODEL_FOCUS) {
            effect(view, gainFocus, direction, previouslyFocusedRect);
        }
    }

    public void dispatchSetEffect(View view, boolean flag) {
        if (effectModel == EFFECT_MODEL_MANUAL) {
            effect(view, flag, -1, null);
        }
    }

    public void setEffectModel(int effectModel) {
        this.effectModel = effectModel;
    }

}
