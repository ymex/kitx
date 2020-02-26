package cn.ymex.kitx.sample.anhttp

import android.os.Bundle
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
import cn.ymex.kitx.sample.anhttp.viewmodel.LoginVMFactory
import cn.ymex.kitx.sample.anhttp.viewmodel.LoginViewModel
import cn.ymex.kitx.widget.glide.GlideImageView
import kotlinx.android.synthetic.main.activity_anhttp.*


class AnhttpActivity : BaseHttpActivity() {

    private val loginViewModel: LoginViewModel by viewModels { LoginVMFactory }
    val delegateAdapter = DelegateAdapter.create()
    override fun getViewModels(): MutableList<ViewModel> {
        return mutableListOf(loginViewModel)
    }


    override fun onCreateView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_anhttp
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vRefresh.setOnRefreshListener {
            requestImages()
        }

        vRefresh.setOnLoadMoreListener {
            requestImages()
        }

        vRecycler.layoutManager = GridLayoutManager(this,2)
        delegateAdapter.bind(Image::class.java, BingImageBinder())
        delegateAdapter.attachRecyclerView(vRecycler)
        requestImages()
    }


    override fun onInitViewModel(viewModels: MutableList<ViewModel>?) {
        super.onInitViewModel(viewModels)
        loginViewModel.liveImagesData.observe(this, Observer {
            delegateAdapter.data = it
            finishRefreshLoadMore()
        })
    }


    fun requestImages() {
        loginViewModel.getImages(10)
    }

    fun finishRefreshLoadMore() {
        vRefresh.finishRefresh()
        vRefresh.finishLoadMore()
        vState.showContentView()
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