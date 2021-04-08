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
package cn.ymex.kitx.core.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;


public class ItemViewHolder extends RecyclerView.ViewHolder {

    public View getItemView() {
        return itemView;
    }

    public ItemViewHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T find(@IdRes int id) {
        return getItemView().findViewById(id);
    }

    public static ItemViewHolder create(ViewGroup parent, @LayoutRes int layout) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    /**
     * @deprecated
     * @param inflater inflater
     * @param layout layout
     * @return
     */
    public static ItemViewHolder create(LayoutInflater inflater, @LayoutRes int layout) {
        return new ItemViewHolder(inflater.inflate(layout, null, false));
    }
}