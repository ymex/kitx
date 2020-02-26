package cn.ymex.kitx.sample.anhttp.repository.service

import cn.ymex.kitx.anhttp.Param
import cn.ymex.kitx.sample.anhttp.UserInfo
import cn.ymex.kitx.sample.anhttp.repository.vo.BingImageResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ApiService {
    /**
     * 登录
     */
    @POST("staff/index/login")
    fun login(@Body param: Param):Call<UserInfo?>


    @POST("HPImageArchive.aspx")
    fun getImages(@QueryMap param: Param):Call<BingImageResult?>

}