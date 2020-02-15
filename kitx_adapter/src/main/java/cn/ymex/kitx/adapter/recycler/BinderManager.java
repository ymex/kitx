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


import androidx.collection.ArrayMap;
import androidx.collection.SparseArrayCompat;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ymex on 2017/08/12.
 * About: binder manager
 */
public class BinderManager {
    private AtomicInteger index = new AtomicInteger(0);

    private SparseArrayCompat<ItemViewBinder<?, ? extends ItemViewHolder>> holderMap;
    private ArrayMap<String, Integer> typeNameMap;

    public SparseArrayCompat<ItemViewBinder<?, ? extends ItemViewHolder>> getHolderMap() {
        if (holderMap == null) {
            holderMap = new SparseArrayCompat<>();
        }
        return holderMap;
    }

    public ArrayMap<String, Integer> getTypeNameMap() {
        if (typeNameMap == null) {
            typeNameMap = new ArrayMap<>();
        }
        return typeNameMap;
    }

    public synchronized void remove(String typeName) {
        Integer type = getTypeNameMap().get(typeName);
        if (type == null) {
            return;
        }
        getTypeNameMap().remove(type);
        getHolderMap().remove(type);
    }


    public synchronized void put(Class clss, ItemViewBinder<?, ? extends ItemViewHolder> binder) {
        put(clss.getName(), binder);
    }

    public synchronized void put(String typeName, ItemViewBinder<?, ? extends ItemViewHolder> binder) {

        Integer type = getTypeNameMap().get(typeName);
        if (type != null) {
            return;
        }
        type = index.incrementAndGet();
        getTypeNameMap().put(typeName, type);
        getHolderMap().put(type, binder);
    }


    public ItemViewBinder<?, ? extends ItemViewHolder> get(Class typeName) {
        return get(typeName.getName());
    }

    public ItemViewBinder<?, ? extends ItemViewHolder> get(String typeName) {
        return findBinnder(typeName);
    }

    public ItemViewBinder<?, ? extends ItemViewHolder> get(int type) {
        return findBinnder(type);
    }


    public Integer getType(String typeName) {
        return findType(typeName);
    }


    private Integer findType(String typeName) {
        return getTypeNameMap().get(typeName);
    }


    private ItemViewBinder<?, ? extends ItemViewHolder> findBinnder(String typeName) {
        Integer type = getTypeNameMap().get(typeName);
        return type != null ? getHolderMap().get(type) : null;
    }

    private ItemViewBinder<?, ? extends ItemViewHolder> findBinnder(int type) {
        return getHolderMap().get(type);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = getTypeNameMap().size(); i < size; i++) {
            String key = getTypeNameMap().keyAt(i);
            int type = getTypeNameMap().get(key);
            builder.append("type=" + type + " name:" + key + "  bind:" + getHolderMap().get(type)).append("\n");
        }
        return "BinderManager{" + builder.toString() + '}';
    }

}
