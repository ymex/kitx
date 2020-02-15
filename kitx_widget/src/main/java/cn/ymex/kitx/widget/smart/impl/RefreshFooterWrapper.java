package cn.ymex.kitx.widget.smart.impl;

import android.annotation.SuppressLint;
import android.view.View;

import cn.ymex.kitx.widget.smart.api.RefreshFooter;
import cn.ymex.kitx.widget.smart.internal.InternalAbstract;

/**
 * 刷新底部包装
 * Created by SCWANG on 2017/5/26.
 */
@SuppressLint("ViewConstructor")
public class RefreshFooterWrapper extends InternalAbstract implements RefreshFooter/*, InvocationHandler */{

    public RefreshFooterWrapper(View wrapper) {
        super(wrapper);
    }

//    @Override
//    public boolean setNoMoreData(boolean noMoreData) {
//        return mWrappedInternal instanceof RefreshFooter && ((RefreshFooter) mWrappedInternal).setNoMoreData(noMoreData);
//    }

}
