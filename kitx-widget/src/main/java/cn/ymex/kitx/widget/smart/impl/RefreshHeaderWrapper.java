package cn.ymex.kitx.widget.smart.impl;

import android.annotation.SuppressLint;
import android.view.View;

import cn.ymex.kitx.widget.smart.api.RefreshHeader;
import cn.ymex.kitx.widget.smart.internal.InternalAbstract;

/**
 * 刷新头部包装
 * Created by SCWANG on 2017/5/26.
 */
@SuppressLint("ViewConstructor")
public class RefreshHeaderWrapper extends InternalAbstract implements RefreshHeader/*, InvocationHandler*/ {

    public RefreshHeaderWrapper(View wrapper) {
        super(wrapper);
    }

}
