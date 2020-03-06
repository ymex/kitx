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
import cn.ymex.kitx.sample.anhttp.viewmodel.ApiViewModel
import cn.ymex.kitx.sample.anhttp.viewmodel.LoginVMFactory
import cn.ymex.kitx.widget.glide.GlideImageView
import kotlinx.android.synthetic.main.activity_anhttp.*


class AnhttpActivity : BaseHttpActivity() {

    private val apiViewModel: ApiViewModel by viewModels { LoginVMFactory }
    val delegateAdapter = DelegateAdapter.create()
    override fun getViewModels(): MutableList<ViewModel> {
        return mutableListOf(apiViewModel)
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

        vRecycler.layoutManager = GridLayoutManager(this, 2)
        delegateAdapter.bind(Image::class.java, BingImageBinder())
        delegateAdapter.attachRecyclerView(vRecycler)
        requestImages()

//方式 1
//        anHttpRequest<ApiService, UserInfo?>({
//            val param = Param.stream()
//            it.login(param)
//        }, HttpResponse(response = {
//
//        }, failure = {
//
//        }, start = {
//
//        }))

//方式2
//        anHttpRequest<UserInfo?>(
//            {
//                ApiRepos(createRetrofitService()).login("", "", "")
//            }, HttpResponse(response = {}, failure = {}, start = {})
//        )
//方式3
//        anHttpRequest<UserInfo?>({
//            ApiRepos(createRetrofitService()).login("", "", "")
//        }, anHttpResponse {
//
//        })

    }


    override fun observeViewModel(viewModels: MutableList<ViewModel>?) {
        super.observeViewModel(viewModels)
        apiViewModel.liveImagesData.observe(this, Observer {
            delegateAdapter.data = it
            finishRefreshLoadMore()
        })
    }


    fun requestImages() {
        apiViewModel.getImages(10)
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