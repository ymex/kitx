package cn.ymex.kitx.widget.smart.listener;

import androidx.annotation.NonNull;

import cn.ymex.kitx.widget.smart.api.RefreshLayout;

/**
 * 刷新监听器
 * Created by SCWANG on 2017/5/26.
 */

public interface OnRefreshListener {
    void onRefresh(@NonNull RefreshLayout refreshLayout);
}
