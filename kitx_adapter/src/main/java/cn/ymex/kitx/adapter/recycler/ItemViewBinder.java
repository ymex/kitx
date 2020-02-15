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
package cn.ymex.kitx.adapter.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by ymex on 2017/08/12.
 * About: ItemViewBinder
 */


public abstract class ItemViewBinder<E, VH extends ItemViewHolder> {

    private DelegateAdapter adapter;

    @NonNull
    public abstract VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    public abstract void onBindViewHolder(@NonNull VH holder, @NonNull E item, int position);

    public void onBindViewHolder(@NonNull ItemViewHolder holder, @NonNull Object item, int position, List<Object> payloads) {
        onBindViewHolder((VH) holder, (E) item, position);
    }

    public DelegateAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(DelegateAdapter adapter) {
        this.adapter = adapter;
    }

    public E getItem(int position) {
        if (adapter.getItemCount() <= position || position < 0) {
            return null;
        }
        return (E) adapter.getItem(position);
    }

    /**
     * Called when a view created by this adapter has been recycled.
     * <p>
     * <p>A view is recycled when a {@link RecyclerView.LayoutManager} decides that it no longer
     * needs to be attached to its parent {@link RecyclerView}. This can be because it has
     * fallen out of visibility or a set of cached views represented by views still
     * attached to the parent RecyclerView. If an item view has large or expensive data
     * bound to it such as large bitmaps, this may be a good place to release those
     * resources.</p>
     * <p>
     * RecyclerView calls this method right before clearing ViewHolder's internal data and
     * sending it to RecycledViewPool. This way, if ViewHolder was holding valid information
     * before being recycled, you can call {@link RecyclerView.ViewHolder#getAdapterPosition()} to get
     * its adapter position.
     *
     * @param holder The ViewHolder for the view being recycled
     */
    public void onViewRecycled(ItemViewHolder holder) {
    }


    public void onViewAttachedToWindow(ItemViewHolder holder) {
    }

    /**
     * Called when a view created by this adapter has been detached from its window.
     * <p>
     * <p>Becoming detached from the window is not necessarily a permanent condition;
     * the consumer of an Adapter's views may choose to cache views offscreen while they
     * are not visible, attaching and detaching them as appropriate.</p>
     *
     * @param holder Holder of the view being detached
     */
    public void onViewDetachedFromWindow(ItemViewHolder holder) {
    }


    public boolean onFailedToRecycleView(ItemViewHolder holder) {
        return false;
    }
}
