package cn.ymex.kitx.sample.anhttp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cn.ymex.kitx.anhttp.*
import cn.ymex.kitx.anhttp.lifecycle.StateViewModel
import cn.ymex.kitx.sample.anhttp.UserInfo
import cn.ymex.kitx.sample.anhttp.repository.ApiRepos
import cn.ymex.kitx.sample.anhttp.repository.service.ApiService
import cn.ymex.kitx.sample.anhttp.repository.vo.BingImageResult
import cn.ymex.kitx.sample.anhttp.repository.vo.Image
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.concurrent.thread


class ApiViewModel(val apiRepos: ApiRepos) : StateViewModel() {

    val liveImagesData = MutableLiveData<List<Image>>()

    fun login(account: String, password: String, type: String) {


        anHttpRequest<UserInfo?>({
            apiRepos.login(account, password, type)
        }, anHttpResponse {

        })

        anHttpRequest<UserInfo?>({
            apiRepos.login(account, password, type)
        }, anHttpResponse(loading = false) {

        })
    }

    fun getImages(size: Int) {

        //使用协程支持的http请求
        httpLaunch{
            val result = async { apiRepos.getImages2("js", 0, size) }
            liveImagesData.value = result.await().images
        }

        //异步http请求
//        anHttpRequest<BingImageResult?>({
//            apiRepos.getImages("js", 0, size)
//        }, anHttpResponse {
//            it?.run {
//                liveImagesData.value = images
//            }
//        })
    }
}

/**
 * Factory for [LoginVMFactory].
 */
object LoginVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return ApiViewModel(ApiRepos(createRetrofitService())) as T
    }
}