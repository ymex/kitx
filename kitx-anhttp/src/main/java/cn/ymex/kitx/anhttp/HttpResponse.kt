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
    val interceptResponse:(response:Response<T>)->Boolean,
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
            if (interceptResponse(response)) {
                vm?.run {
                    stater.postValue(PageState(ViewStatus.NORMAL))
                }
                return
            }
            val body = response.body()
            body?.run {
                response.headers()
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


/**
 * @param start  开始网络请求事件处理回调， 当返回true 时表示不再走ViewModel统一处理方法
 * @param complete 收到网络响应结果事件处理回调， 当返回true 时表示不再走ViewModel统一处理方法
 * @param failure 异常抛出事件处理回调， 当返回true 时表示不再走ViewModel统一处理方法
 */
class HttpLaunchCallBack(
    val start: () -> Boolean = {false},
    val complete: () -> Boolean = {false},
    val failure: (t: Throwable) -> Boolean ={false},
    private val vm: StateViewModel? = null,
):LaunchCallBack{

    override fun onStart() {
        val intercept = start()
        if (intercept){
            return
        }
        vm?.run {
                stater.postValue(PageState(ViewStatus.LOADING))
        }

    }

    override fun onFailure(t: Throwable) {
        val intercept = failure(t)
        if (intercept){
            return
        }
        vm?.run {
            stater.postValue(PageState(ViewStatus.ERR, t))
        }
    }

    override fun onComplete() {
        val intercept =  complete()
        if (intercept){
            return
        }
        vm?.run {
            stater.postValue(PageState(ViewStatus.NORMAL))
        }
    }
}