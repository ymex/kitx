package cn.ymex.kitx.sample.anhttp.repository.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.ymex.kitx.anhttp.lifecycle.LifeViewModel
import cn.ymex.kitx.anhttp.anHttpRequest
import cn.ymex.kitx.anhttp.anHttpResponse
import cn.ymex.kitx.anhttp.createRetrofitService
import cn.ymex.kitx.anhttp.lifecycle.StateViewModel
import cn.ymex.kitx.sample.anhttp.UserInfo
import cn.ymex.kitx.sample.anhttp.repository.ApiRepos


class LoginViewModel(val apiRepos: ApiRepos) : StateViewModel() {

    fun login(account: String, password: String, type: String) {

        anHttpRequest<UserInfo?>({
            apiRepos.login(account, password, type)
        }, anHttpResponse {

        })
    }
}

/**
 * Factory for [LoginVMFactory].
 */
object LoginVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return LoginViewModel(ApiRepos(createRetrofitService())) as T
    }
}