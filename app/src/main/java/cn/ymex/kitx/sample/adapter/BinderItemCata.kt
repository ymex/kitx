package cn.ymex.kitx.sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import cn.ymex.kitx.adapter.recycler.ItemViewBinder
import cn.ymex.kitx.adapter.recycler.ItemViewHolder
import cn.ymex.kitx.sample.R
import cn.ymex.kitx.utils.hideInputKeyBoard
import cn.ymex.kitx.utils.setOnClickThrottleListener

/**
 * Created by ymex on 2020/2/13.
 * About:
 */
class BinderItemCata :ItemViewBinder<String,ItemViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ItemViewHolder {
        return ItemViewHolder.create(parent, R.layout.item_binder_cata)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, item: String, position: Int) {
        val tvTitle = holder.find<TextView>(R.id.tvTitle)
        tvTitle.text = item

        holder.getItemView().setOnClickThrottleListener {
            holder.getItemView().context.hideInputKeyBoard(holder.getItemView())
            println("-----------------:"+System.currentTimeMillis())
        }
    }
}