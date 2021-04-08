package cn.ymex.kitx.widget.glide;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cn.ymex.kitx.widget.glide.transformations.RoundedCornersTransformation;


/**
 * Created by ymex on 2018/7/20.
 * About:
 */
public class Image {

    public static RequestOptions options() {
        return new RequestOptions();
    }

    public static RequestOptions options(@DrawableRes int placeholder, int error) {
        return RequestOptions.placeholderOf(placeholder).error(error);
    }

    public static void load(String url, ImageView view, RequestOptions options) {
        Glide.with(view).load(url).apply(options).into(view);
    }

    public static void load(String url, ImageView view) {
        Glide.with(view).load(url).into(view);
    }

    /**
     * 加载占位图，失败图是同一图
     *
     * @param resId
     * @return
     */
    public static RequestOptions options(@DrawableRes int resId) {
        return RequestOptions.placeholderOf(resId).error(resId);
    }


    /**
     * 圆角
     *
     * @param radius
     * @return
     */
    public static RequestOptions round(int radius) {
        return RequestOptions.bitmapTransform(new RoundedCornersTransformation(radius, 0));
    }

    /**
     * 裁剪在圆形
     *
     * @return
     */
    public static RequestOptions circle() {
        return RequestOptions.circleCropTransform();
    }
}
