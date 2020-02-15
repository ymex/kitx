package cn.ymex.kitx.sample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.ymex.kitx.core.adapter.recycler.ItemViewBinder
import cn.ymex.kitx.core.adapter.recycler.ItemViewHolder
import cn.ymex.kitx.sample.R

data class ActInt(val name: String, val block: (view: View) -> Unit = {})
class BinderItemAction : ItemViewBinder<ActInt, ItemViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ItemViewHolder {
        return ItemViewHolder.create(parent, R.layout.item_binder_cata)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, item: ActInt, position: Int) {
        val tvTitle = holder.find<TextView>(R.id.tvTitle)
        tvTitle.text = item.name
        holder.getItemView().setOnClickListener {
            item.block(it)
        }

    }
}