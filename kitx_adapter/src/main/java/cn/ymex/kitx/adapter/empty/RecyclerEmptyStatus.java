package cn.ymex.kitx.adapter.empty;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.ymex.kitx.adapter.WrapType;
import cn.ymex.kitx.adapter.recycler.DelegateAdapter;
import cn.ymex.kitx.adapter.recycler.ItemViewBinder;

/**
 * Created by ymexc on 2018/8/23.
 * About:AdapterDataObserver
 * demo:
 *      RecyclerEmptyStatus.attach(rvContent).data(EmptyDataWrap<String>("暂无通知")).binder(EmptyDataBinder()).notifyData()
 */
public class RecyclerEmptyStatus extends RecyclerView.AdapterDataObserver {

    private ItemViewBinder emptyBinder;
    private RecyclerView recyclerView;
    private EmptyDataWrap emptyData;

    public RecyclerEmptyStatus(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        RecyclerView.Adapter adapter = this.recyclerView.getAdapter();
        if (adapter.hasObservers()) {
            Log.e("cn.ymex.kitx.adapter", "recyclerview alread has observers.");
        }
        adapter.registerAdapterDataObserver(this);

    }

    public RecyclerEmptyStatus data(EmptyDataWrap emptyData) {
        this.emptyData = emptyData;
        return this;
    }

    public EmptyDataWrap getData() {
        return emptyData;
    }

    public RecyclerEmptyStatus binder(@NonNull ItemViewBinder emptyBinder) {
        this.emptyBinder = emptyBinder;
        RecyclerView.Adapter adapter = this.recyclerView.getAdapter();
        if (adapter instanceof DelegateAdapter) {
            ((DelegateAdapter) adapter).bind(EmptyDataWrap.class, emptyBinder);
        }
        return this;
    }

    public void notifyData() {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            return;
        }
        if (this.emptyBinder == null) {
            return;
        }
        if (adapter instanceof DelegateAdapter) {
            if (adapter.getItemCount() <= 0) {
                ((DelegateAdapter) adapter).getData().add(emptyData == null ? new EmptyDataWrap() : emptyData);
                adapter.notifyDataSetChanged();
            } else {
                WrapType<EmptyDataWrap> wt = ((DelegateAdapter) adapter).getItemData(EmptyDataWrap.class);
                if (wt != null) {
                    if (adapter.getItemCount() >= 2) {
                        ((DelegateAdapter) adapter).getData().remove(wt.getIndex());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }

    }

    public void unregistRecyclerEmpty() {
        this.recyclerView.getAdapter().unregisterAdapterDataObserver(this);
    }

    public static RecyclerEmptyStatus attach(RecyclerView recyclerView) {
        if (recyclerView.getAdapter() == null) {
            throw new IllegalArgumentException("please setAdapter for RecyclerView first.");
        }
        return new RecyclerEmptyStatus(recyclerView);
    }

    @Override
    public void onChanged() {
        super.onChanged();
        notifyData();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        notifyData();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        notifyData();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        notifyData();
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        notifyData();
    }
}
