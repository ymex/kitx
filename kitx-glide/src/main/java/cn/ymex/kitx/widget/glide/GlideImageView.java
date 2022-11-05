package cn.ymex.kitx.widget.glide;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;


import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import cn.ymex.kitx.widget.glide.transformations.RoundedCornersTransformation;


/**
 * @author ymexc 2018/6/29
 * GlideImageView
 */
public class GlideImageView extends FrameLayout implements RequestListener<Drawable> {

    //todo 进度条
    //todo gif 支持？
    private ImageView contentImageView;
    RequestManager requestManager;

    private static final ImageView.ScaleType[] sScaleTypeArray = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };

    //缩放类型
    private ImageView.ScaleType scaleType;
    //图片链接
    private String imageUrl;
    //自动加载图
    //private boolean autoLoad;
    //<0圆形，=0正常，>0:圆角
    private int roundRadius;
    private Drawable placeholderRes;
    private Drawable errorRes;

    public GlideImageView(@NonNull Context context) {
        this(context, null);
    }

    public GlideImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GlideImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GlideImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (!isInEditMode()){
            requestManager = Glide.with(this);
        }

        contentImageView = new ImageView(getContext(), attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GlideImageView);

        final boolean autoLoad = a.getBoolean(R.styleable.GlideImageView_glide_auto_load, true);

        roundRadius = a.getDimensionPixelSize(R.styleable.GlideImageView_glide_round_radius, 0);
        placeholderRes = a.getDrawable(R.styleable.GlideImageView_glide_placeholder);
        errorRes = a.getDrawable(R.styleable.GlideImageView_glide_error);


        if (autoLoad) {
            final int urlRes = a.getResourceId(R.styleable.GlideImageView_glide_src,-1);
            if (urlRes==-1){
                final String url = a.getString(R.styleable.GlideImageView_glide_src);
                if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
                    setImageUrl(url, options());
                }
            }else {
                final Drawable d = a.getDrawable(R.styleable.GlideImageView_glide_src);
                if (d != null) {
                    setImageDrawable(d, options());
                }
            }
        }

        final int index = a.getInt(R.styleable.GlideImageView_glide_scale_type, -1);
        if (index >= 0) {
            setScaleType(sScaleTypeArray[index]);
        }
        a.recycle();

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(contentImageView, layoutParams);
    }


    @SuppressLint("CheckResult")
    private RequestOptions options() {
        RequestOptions op = Image.options();
        if (roundRadius < 0) {
            op.circleCrop();
        }
        if (roundRadius > 0) {
            op.transform(new RoundedCornersTransformation(roundRadius, 0));
        }
        if (placeholderRes != null) {
            op.placeholder(placeholderRes);
        }
        if (errorRes != null) {
            op.error(errorRes);
        }
        return op;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        contentImageView.setScaleType(scaleType);
    }

    /**
     * 加载bitmap
     *
     * @param bitmap
     * @param requestOptions
     */
    public void setImageBitmap(Bitmap bitmap, RequestOptions requestOptions) {
        if (requestOptions == null) {
            requestOptions = options();
        }
        requestManager.load(bitmap).apply(requestOptions).listener(this).into(contentImageView);
    }

    /**
     * 加载Drawable
     *
     * @param imageDrawable
     * @param requestOptions
     */
    public void setImageDrawable(Drawable imageDrawable, RequestOptions requestOptions) {

        if (requestOptions == null) {
            requestOptions = options();
        }
        if (isInEditMode()){
            contentImageView.setImageDrawable(imageDrawable);
            return;
        }
        requestManager.load(imageDrawable).apply(requestOptions).listener(this).into(contentImageView);
    }


    /**
     * 加载资源图片
     *
     * @param resId
     * @param requestOptions
     */
    public void setImageResource(@DrawableRes int resId, RequestOptions requestOptions) {

        if (requestOptions == null) {
            requestOptions = options();
        }
        requestManager.load(resId).apply(requestOptions).listener(this).into(contentImageView);

    }

    /**
     * 加载网络图片
     *
     * @param url
     */
    public void setImageUrl(String url) {
        setImageUrl(url, null);
    }


    /**
     * 加载网络图片
     *
     * @param url
     * @param requestOptions
     */
    public void setImageUrl(String url, RequestOptions requestOptions) {
        this.imageUrl = url;
        if (requestOptions == null) {
            requestOptions = options();
        }
        requestManager.load(imageUrl)
                .apply(requestOptions)
                .listener(this).into(contentImageView);
    }

    /**
     * 加载文件图片
     *
     * @param file
     * @param requestOptions
     */
    private void setImageFile(@Nullable File file, RequestOptions requestOptions) {
        if (requestOptions == null) {
            requestOptions = options();
        }
        requestManager.load(file).apply(requestOptions).listener(this).into(contentImageView);
    }

    public ImageView getContentImageView() {
        return contentImageView;
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        return false;
    }
}
