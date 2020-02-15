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


package cn.ymex.kitx.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import java.util.ArrayList;
import java.util.List;


/**
 * Adapter for ListView
 *
 * @param <E> 实体Bean
 * @param <V> ViewHodler
 */
public abstract class ListViewAdapter<E extends Object, V extends ListViewAdapter.ViewHolder>
        extends BaseAdapter {

    private ViewGroup hostView;
    private boolean isAutoNotifyDataSetChanged = true;
    protected List<E> mData; //数据列表
    private Context mContext;

    public ListViewAdapter(Context context, List<E> dataList) {
        super();
        this.mContext = context;
        this.mData = dataList;
    }

    public ListViewAdapter() {
        this(null, null);
    }


    public ListViewAdapter(List<E> dataList) {
        this(null, dataList);
    }

    public ListViewAdapter(Context context) {
        this(context, null);
    }

    public List<E> getData() {
        if (mData == null) {
            mData = new ArrayList<E>();
        }
        return mData;
    }

    /**
     * onCreateViewHolder
     *
     * @param parent   parent
     * @param position position
     * @return ViewHolder
     */
    public abstract V onCreateViewHolder(ViewGroup parent, int position);

    /**
     * onBindViewHolder
     *
     * @param position    position
     * @param convertView convertView
     * @param parent      parent
     * @param hold        hold
     */
    public abstract void onBindViewHolder(int position, View convertView, ViewGroup parent, V hold);


    private boolean isNull(Object o) {
        return o == null;
    }

    /**
     * reset entities
     *
     * @param data
     */
    public void setData(List<E> data) {
        if (isNull(data)) {
            Log.e("error", "At ListViewAdapter.resetData(): null ");
            return;
        }

        this.getData().clear();
        this.mData.addAll(data);
        if (isAutoNotifyDataSetChanged) {
            this.notifyDataSetChanged();
        }
    }


    /**
     * append entities
     *
     * @param data
     * @deprecated
     */
    public void appendData(List<E> data) {
        appendDataList(data);
    }

    /**
     * append entities
     *
     * @param data
     */
    public void appendDataList(List<E> data) {
        if (isNull(data)) {
            Log.e("error", "At ListViewAdapter.appendData():  null ");
            return;
        }
        this.getData().addAll(data);
        if (isAutoNotifyDataSetChanged) {
            this.notifyDataSetChanged();
        }
    }

    /**
     * add single data entity
     * 局部刷新这一条目
     *
     * @param data
     */
    public void appendData(E data) {
        if (isNull(data)) {
            Log.e("error", "At ListViewAdapter.appendData(): java.lang.NullPointerException: Attempt to invoke interface method 'java.lang.Object[] java.util.Collection.toArray()' on a null ");
            return;
        }
        this.getData().add(data);
        if (isAutoNotifyDataSetChanged) {
            this.notifyDataSetChanged(this.getData().size() - 1);
        }
    }


    @Override
    public int getCount() {
        return getData().size();
    }

    /**
     * set item data
     *
     * @param position position
     * @param data     data
     */
    public void setItemData(int position, E data) {
        mData.set(position, data);
        if (isAutoNotifyDataSetChanged) {
            this.notifyDataSetChanged(position);
        }
    }

    /**
     * remove item data
     * @param position position
     */
    public void removeItemData(int position) {
        mData.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public E getItem(int position) {
        if (position > getCount() || position < 0) {
            throw new ArrayIndexOutOfBoundsException(position);//数组越界
        }
        return getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (hostView == null) {
            hostView = parent;
        }
        V viewHolder = null;
        if (convertView == null) {
            viewHolder = onCreateViewHolder(parent, position);
            convertView = viewHolder.getRootView();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (V) convertView.getTag();
        }
        onBindViewHolder(position, convertView, parent, viewHolder);
        return convertView;
    }

    /**
     * 布局缓存类
     */
    public static class ViewHolder {
        private View rootView;

        public ViewHolder(View view) {
            this.rootView = view;
        }

        public View getRootView() {
            return rootView;
        }

        public <T extends View> T find(@IdRes int id) {
            return (T) rootView.findViewById(id);
        }

        public static ViewHolder create(ViewGroup parent, @LayoutRes int layout) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        }
    }

    /**
     * 局部更新数据
     *
     * @param position 位置
     */
    public void notifyDataSetChanged(int position) {
        ListView listView = ((ListView) hostView);
        if (listView == null) {
            return;
        }
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int lastVisiblePosition = listView.getLastVisiblePosition();

        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            View view = listView.getChildAt(position - firstVisiblePosition);
            getView(position, view, listView);
        }
    }

    /**
     * 添加数据还是否自动调用notifyDataSetChanged
     *
     * @param autoNotifyDataSetChanged notifyDataSetChanged
     */
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
