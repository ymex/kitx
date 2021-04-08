/*
 *     Copyright 2017 www.ymex.cn
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package cn.ymex.kitx.core.adapter;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.ymex.kitx.core.adapter.recycler.ItemViewHolder;


/**
 * Adapter for RecyclerView
 *
 * @param <VH> RecyclerView viewHolder
 */

public abstract class RecyclerAdapter<E, VH extends ItemViewHolder> extends RecyclerView.Adapter<VH> {

    private List<E> mData; //数据列表
    private boolean isAutoNotifyDataSetChanged = true;
    protected Context mContext;

    public RecyclerAdapter(Context context, List<E> data) {
        super();
        this.mContext = context;
        this.mData = data;
    }

    public RecyclerAdapter() {
        this(null, null);
    }

    public RecyclerAdapter(Context context) {
        this(context, null);
    }

    public RecyclerAdapter(List<E> data) {
        this(null, data);
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public List<E> getData() {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        return mData;
    }


    /**
     * append entities
     *
     * @param data list data
     */
    public void appendDataList(List<? extends E> data) {
        if (isNull(data)) {
            Log.e("error", "At RecyclerAdapter.appendData(data): data is null ");
            return;
        }
        this.getData().addAll(data);
        if (isAutoNotifyDataSetChanged) {
            this.notifyDataSetChanged();
        }
    }

    public void appendData(E data) {
        if (isNull(data)) {
            Log.e("error", "At RecyclerAdapter.appendData(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
            return;
        }
        this.getData().add(data);
        if (isAutoNotifyDataSetChanged) {
            this.notifyItemChanged(this.getData().size() - 1);
        }
    }

    /**
     * remove item data
     *
     * @param position position
     */
    public void removeItemData(int position) {
        mData.remove(position);
        this.notifyItemRemoved(position);
    }


    public void removeItemDatas(int startIndex, int endIndex) {
        mData.removeAll(mData.subList(startIndex, endIndex));
        this.notifyItemRangeRemoved(startIndex,endIndex-startIndex);
    }

    /**
     * set item data
     *
     * @param position position
     * @param data     data
     */
    public void setItemData(int position, E data) {
        mData.set(position, data);
        this.notifyItemChanged(position);
    }


    public E getItem(int position) {
        if (position > getItemCount() || position < 0) {
            throw new ArrayIndexOutOfBoundsException(position);//数组越界
        }
        return getData().get(position);
    }

    public void setData(List<? extends E> data) {
        if (isNull(data)) {
            Log.e("error", "At RecyclerAdapter.setData(data): data is null ");
            return;
        }

        this.getData().clear();
        this.mData.addAll(data);
        if (isAutoNotifyDataSetChanged) {
            this.notifyDataSetChanged();
        }
    }

    private boolean isNull(Object o) {
        return o == null;
    }

    public void setAutoNotifyDataSetChanged(boolean autoNotifyDataSetChanged) {
        isAutoNotifyDataSetChanged = autoNotifyDataSetChanged;
    }

    public Context getContext() {
        if (isNull(this.mContext)) {
            throw new IllegalArgumentException("listViewAdapter context is null");
        }
        return mContext;
    }
}

