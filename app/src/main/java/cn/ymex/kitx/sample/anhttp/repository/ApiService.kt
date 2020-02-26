package cn.ymex.kitx.sample.anhttp.repository

import cn.ymex.kitx.anhttp.Param
import cn.ymex.kitx.sample.anhttp.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    /**
     * 登录
     */
    @POST("staff/index/login")
    fun login(@Body param: Param):Call<UserInfo?>
}