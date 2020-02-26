package cn.ymex.kitx.sample.anhttp.repository

import cn.ymex.kitx.anhttp.Param
import cn.ymex.kitx.sample.anhttp.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ymex on 2020/2/26.
 * About:
 */
class ApiRepos(val service: ApiService) {
    fun login(account: String, password: String, type: String): Call<UserInfo?> {
        val param = Param.stream()
            .with("userPhone", "18963793507")
            .with("userPwd", "123456")
            .with("clinicCode", "bhjd")

        val c = service.login(param)




        return service.login(param)
    }
}