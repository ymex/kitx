package cn.ymex.kitx.anhttp

import cn.ymex.kitx.anhttp.lifecycle.PageState
import cn.ymex.kitx.anhttp.lifecycle.StateViewModel
import cn.ymex.kitx.anhttp.lifecycle.ViewStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ResponseCallback<T> : Callback<T> {
    fun onStart()
}

class HttpResponse<T>(
    val response: (data: T) -> Unit,
    val failure: (t: Throwable) -> Unit,
    val start: () -> Unit,
    private val vm: StateViewModel? = null,
    private val load: Boolean = true
) : ResponseCallback<T> {


    override fun onStart() {
        vm?.run {
            if (load) {
                stater.postValue(PageState(ViewStatus.LOADING))
            }
        }
        start()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        failure(t)
        vm?.run {
            stater.postValue(PageState(ViewStatus.ERR, t))
        }
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        try {
            val body = response.body()
            body?.run {
                response(this)
            } ?: onFailure(call, Exception("$call : response.body() is null ."))
            vm?.run {
                stater.postValue(PageState(ViewStatus.NORMAL))
            }
        } catch (e: Exception) {
            onFailure(call, e)
        }
    }
}