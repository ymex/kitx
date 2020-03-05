package cn.ymex.kitx.sample.anhttp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.ymex.kitx.anhttp.anHttpRequest
import cn.ymex.kitx.anhttp.anHttpResponse
import cn.ymex.kitx.anhttp.createRetrofitService
import cn.ymex.kitx.anhttp.lifecycle.StateViewModel
import cn.ymex.kitx.sample.anhttp.UserInfo
import cn.ymex.kitx.sample.anhttp.repository.ApiRepos
import cn.ymex.kitx.sample.anhttp.repository.vo.BingImageResult
import cn.ymex.kitx.sample.anhttp.repository.vo.Image


class ApiViewModel(val apiRepos: ApiRepos) : StateViewModel() {

    val liveImagesData = MutableLiveData<List<Image>>()

    fun login(account: String, password: String, type: String) {
        anHttpRequest<UserInfo?>(
            anHttpResponse {

            }
        ) {
            apiRepos.login(account, password, type)
        }
    }

    fun getImages(size: Int) {
        anHttpRequest<BingImageResult?>(
            anHttpResponse {

                it?.run {
                    liveImagesData.value = images
                }

            }) {
            apiRepos.getImages("js", 0, size)
        }
    }
}

/**
 * Factory for [LoginVMFactory].
 */
object LoginVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ApiViewModel(ApiRepos(createRetrofitService())) as T
    }
}