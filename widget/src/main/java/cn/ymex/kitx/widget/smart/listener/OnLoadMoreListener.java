package cn.ymex.kitx.widget.smart.listener;

import androidx.annotation.NonNull;

import cn.ymex.kitx.widget.smart.api.RefreshLayout;

/**
 * 加载更多监听器
 * Created by SCWANG on 2017/5/26.
 */

public interface OnLoadMoreListener {
    void onLoadMore(@NonNull RefreshLayout refreshLayout);
}
