package cn.ymex.kitx.sample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import cn.ymex.kitx.core.adapter.recycler.ItemViewBinder
import cn.ymex.kitx.core.adapter.recycler.ItemViewHolder
import cn.ymex.kitx.sample.R

/**
 * Created by ymex on 2020/2/13.
 * About:
 */

data class Video(val name:String)

class BinderItemVideo :ItemViewBinder<Video,ItemViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ItemViewHolder {
        return ItemViewHolder.create(parent, R.layout.item_binder_video)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, item: Video, position: Int) {
        val tvTitle = holder.find<TextView>(R.id.tvTitle)
        tvTitle.text = item.name
    }
}