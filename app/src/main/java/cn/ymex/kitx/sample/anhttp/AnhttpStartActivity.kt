package cn.ymex.kitx.sample.anhttp

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import cn.ymex.kitx.core.adapter.recycler.DelegateAdapter
import cn.ymex.kitx.core.adapter.recycler.ItemViewBinder
import cn.ymex.kitx.core.adapter.recycler.ItemViewHolder
import cn.ymex.kitx.sample.R
import cn.ymex.kitx.sample.anhttp.repository.vo.Image
import cn.ymex.kitx.sample.anhttp.viewmodel.ApiViewModel
import cn.ymex.kitx.sample.anhttp.viewmodel.LoginVMFactory
import cn.ymex.kitx.sample.databinding.ActivityAnhttpBinding
import cn.ymex.kitx.widget.glide.GlideImageView


public class AnhttpStartActivity : BaseHttpStartActivity<ActivityAnhttpBinding>() {


    override fun viewBinding() = ActivityAnhttpBinding.inflate(layoutInflater)


    private val apiViewModel: ApiViewModel by viewModels { LoginVMFactory }

    val delegateAdapter = DelegateAdapter.create()





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = ActivityAnhttpBinding.bind(view)
        vb.vRefresh.setOnRefreshListener {
            requestImages()
        }

        vb.vRefresh.setOnLoadMoreListener {
            requestImages()
        }

        vb.vRecycler.layoutManager = GridLayoutManager(this, 2)
        delegateAdapter.bind(Image::class.java, BingImageBinder())
        delegateAdapter.attachRecyclerView(vb.vRecycler)
        requestImages(true)
    }


    override fun observeViewModel() {
        super.observeViewModel()
        setAnHttpObserver(apiViewModel)
        apiViewModel.liveImagesData.observe(this, Observer {
            delegateAdapter.data = it
            finishRefreshLoadMore()
        })
    }


    fun requestImages(showLoading:Boolean = false) {
        apiViewModel.getImages(10,showLoading)
    }

    fun finishRefreshLoadMore() {
        vb.vRefresh.finishRefresh()
        vb.vRefresh.finishLoadMore()
        vb.vState.showContentView()
    }
}


class BingImageBinder : ItemViewBinder<Image, ItemViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ItemViewHolder {
        return ItemViewHolder.create(parent, R.layout.item_binder_image)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, item: Image, position: Int) {
        val giv = holder.find<GlideImageView>(R.id.givImage)
        val host = "https://cn.bing.com"
        giv.setImageUrl(host + item.url)
    }

}