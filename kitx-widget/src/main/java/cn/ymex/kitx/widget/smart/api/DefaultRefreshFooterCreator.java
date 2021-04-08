package cn.ymex.kitx.widget.smart.api;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 默认Footer创建器
 * Created by SCWANG on 2018/1/26.
 */
public interface DefaultRefreshFooterCreator {
    @NonNull
    RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout);
}
