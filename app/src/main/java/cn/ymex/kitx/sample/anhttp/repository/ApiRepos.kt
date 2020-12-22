package cn.ymex.kitx.sample.anhttp.repository

import cn.ymex.kitx.anhttp.Param
import cn.ymex.kitx.sample.anhttp.UserInfo
import cn.ymex.kitx.sample.anhttp.repository.service.ApiService
import cn.ymex.kitx.sample.anhttp.repository.vo.BingImageResult
import retrofit2.Call
import retrofit2.Response

/**
 * Created by ymex on 2020/2/26.
 * About:
 */
class ApiRepos(val service: ApiService) {
    fun login(account: String, password: String, type: String): Call<UserInfo?> {
        val param = Param.stream()
            .with("userPhone", "")
            .with("userPwd", "")
            .with("clinicCode", "")
        return service.login(param)
    }


    fun getImages(format: String, idx: Int, size: Int) :Call<BingImageResult?>{
        val param = Param.stream().with("format", format).with("idx", idx).with("n", size)
        return service.getImages(param)
    }

    suspend fun getImages2(format: String, idx: Int, size: Int) :BingImageResult{
        val param = Param.stream().with("format", format).with("idx", idx).with("n", size)
        return service.getImages2(param)
    }
}