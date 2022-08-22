package cn.ymex.kitx.sample.anhttp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.ymex.kitx.anhttp.*
import cn.ymex.kitx.anhttp.lifecycle.ActionViewModel
import cn.ymex.kitx.core.lifecycle.MutableLifeData
import cn.ymex.kitx.sample.anhttp.UserInfo
import cn.ymex.kitx.sample.anhttp.repository.ApiRepos
import cn.ymex.kitx.sample.anhttp.repository.vo.Image
import cn.ymex.kitx.sample.anhttp.stateLaunch
import kotlinx.coroutines.*


class ApiViewModel(val apiRepos: ApiRepos) : ActionViewModel() {

    val liveImagesData = MutableLifeData<List<Image>>()

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

    fun getImages(size: Int,loading:Boolean = false) {
        //使用协程支持的http请求
        stateLaunch(loading) {
            delay(3000)
            val result = apiRepos.getImages2("js", 0, size)
            liveImagesData.value = result.images
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