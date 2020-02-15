package cn.ymex.kitx.core.adapter.recycler;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.ymex.kitx.core.adapter.RecyclerAdapter;
import cn.ymex.kitx.core.adapter.Type;
import cn.ymex.kitx.core.adapter.WrapType;

/**
 * Created by ymex on 2017/08/12.
 * About: Delegate Adapter for bind views
 */
public class DelegateAdapter extends RecyclerAdapter<Object, ItemViewHolder> {
    /**
     * extra data
     */
    private Object extraData;
    private BinderManager manager;

    public static DelegateAdapter create(List<? super Object> data) {
        return new DelegateAdapter(data);
    }

    public static DelegateAdapter create() {
        return create(null);
    }


    public void attachRecyclerView(RecyclerView view) {
        view.setAdapter(this);
        this.mContext = view.getContext();
    }

    public DelegateAdapter() {
        this(null);
    }

    public DelegateAdapter(List<? super Object> data) {
        super(null, data);
    }

    public BinderManager getManager() {
        if (manager == null) {
            manager = new BinderManager();
        }
        return manager;
    }

    public DelegateAdapter bind(Class type, ItemViewBinder binder) {
        bind(type, "", binder);
        return this;
    }

    public DelegateAdapter bind(Class type, String typeTag, ItemViewBinder binder) {
        if (binder != null) {
            getManager().put(type.getName() + typeTag, binder);
            binder.setAdapter(this);
        }
        return this;
    }


    public void unBind(Class type) {
        unBind(type, "");
    }

    public void unBind(Class type, String typeTag) {
        getManager().remove(type.getName() + typeTag);
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return getManager().get(viewType).onCreateViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        getManager().get(getItemViewType(position)).onBindViewHolder(holder, getItem(position), position, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() <= 0) {
            return super.getItemViewType(position);
        }
        Object item = getItem(position);
        String typeName = item.getClass().getName();
        if (item instanceof Type) {
            String tag = ((Type) item).getTypeTag();
            typeName += (TextUtils.isEmpty(tag) ? "" : tag);
        }
        Integer type = getManager().getType(typeName);
        if (type == null) {
            /*
             * 类型没有绑定ItemViewBinder
             */
            throw new IllegalArgumentException("con't find the ItemViewBinder of type class " + getItem(position).getClass().getName());
        }
        return type;
    }


    public <T extends Object> T getItemData(int position) {
        return (T) super.getItem(position);
    }

    public <T> WrapType<T> getItemData(Class<T> cls) {
        return getItemData(cls, "");
    }

    /**
     * find first class type and return
     *
     * @param cls class
     * @param tag tag
     * @param <T> data type
     * @return WrapType
     */
    public <T> WrapType<T> getItemData(Class<T> cls, String tag) {
        String typeName = cls.getName() + tag;
        if (TextUtils.isEmpty(typeName)) {
            return null;
        }
        for (int i = 0, size = getItemCount(); i < size; i++) {
            Object item = getItem(i);
            if (item instanceof Type) {
                if (typeName.equals(item.getClass().getName() + ((Type) item).getTypeTag())) {
                    WrapType<T> wrapType = WrapType.create((T) getItem(i), tag);
                    wrapType.setIndex(i);
                    return wrapType;
                }
            } else {
                if (typeName.equals(getItem(i).getClass().getName() + tag)) {
                    WrapType<T> wrapType = WrapType.create((T) getItem(i), tag);
                    wrapType.setIndex(i);
                    return wrapType;
                }
            }
        }
        return null;
    }

    /**
     * get all items data
     * @param cls
     * @param tag
     * @param <T>
     * @return
     */
    public <T> List<WrapType<T>> getItemsData(Class<T> cls, String tag) {
        List<WrapType<T>> sublist = new ArrayList<>();
        String typeName = cls.getName() + tag;
        if (TextUtils.isEmpty(typeName)) {
            return null;
        }
        for (int i = 0, size = getItemCount(); i < size; i++) {
            Object item = getItem(i);
            if (item instanceof Type) {
                if (typeName.equals(item.getClass().getName() + ((Type) item).getTypeTag())) {
                    WrapType<T> wrapType = WrapType.create((T) getItem(i), tag);
                    wrapType.setIndex(i);
                    sublist.add(wrapType);
                }
            } else {
                if (typeName.equals(getItem(i).getClass().getName() + tag)) {
                    WrapType<T> wrapType = WrapType.create((T) getItem(i), tag);
                    wrapType.setIndex(i);
                    sublist.add(wrapType);
                }
            }
        }
        return sublist;
    }

    /**
     * 移除某一类型数据。
     * @param cls
     * @param <T>
     */

    public <T> void removeItemData(Class<T> cls) {
        removeItemData(cls, "");
    }

    /**
     * 移除某一类型数据。
     *
     * @param cls
     * @param tag
     * @param <T>
     */
    public <T> void removeItemData(Class<T> cls, String tag) {
        List sublist = new ArrayList<>();
        String typeName = cls.getName() + tag;
        for (int i = 0, size = getItemCount(); i < size; i++) {
            Object item = getItem(i);
            if (item instanceof Type) {
                if (typeName.equals(item.getClass().getName() + ((Type) item).getTypeTag())) {
                    sublist.add(item);
                }
            } else {
                if (typeName.equals(item.getClass().getName() + tag)) {
                    sublist.add(item);
                }
            }
        }
        getData().removeAll(sublist);
        this.notifyDataSetChanged();
    }


    public void notifyItemChanged(WrapType wrapType) {
        if (wrapType != null && wrapType.getIndex() >= 0) {
            notifyItemChanged(wrapType.getIndex());
        }
    }

    public ItemViewBinder<?, ? extends ItemViewHolder> getBinder(int position) {
        return manager.get(getItemViewType(position));
    }

    public ItemViewBinder<?, ? extends ItemViewHolder> getBinder(Class cls) {
        return manager.get(cls);
    }

    public ItemViewBinder<?, ? extends ItemViewHolder> getBinder(Class cls, String tag) {
        return manager.get(cls.getName() + tag);
    }

    @Override
    public void onViewRecycled(ItemViewHolder holder) {
        super.onViewRecycled(holder);
        ItemViewBinder binder = manager.get(holder.getItemViewType());
        if (manager != null && binder != null) {
            binder.onViewRecycled(holder);
        }
    }

    @Override
    public void onViewAttachedToWindow(ItemViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ItemViewBinder binder = manager.get(holder.getItemViewType());
        if (manager != null && binder != null) {
            binder.onViewAttachedToWindow(holder);
        }
    }

    @Override
    public void onViewDetachedFromWindow(ItemViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ItemViewBinder binder = manager.get(holder.getItemViewType());
        if (manager != null && binder != null) {
            binder.onViewDetachedFromWindow(holder);
        }
    }

    @Override
    public boolean onFailedToRecycleView(ItemViewHolder holder) {
        ItemViewBinder binder = manager.get(holder.getItemViewType());
        if (manager != null && binder != null) {
            return binder.onFailedToRecycleView(holder);
        }
        return super.onFailedToRecycleView(holder);
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

}
