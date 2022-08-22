package cn.ymex.kitx.anhttp

import cn.ymex.kitx.anhttp.lifecycle.LaunchStatus
import cn.ymex.kitx.anhttp.lifecycle.StateData
import cn.ymex.kitx.anhttp.lifecycle.StateViewModel
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
    val interceptResponse: (response: Response<T>) -> Boolean,
    private val vm: StateViewModel? = null,
    private val load: Boolean = true
) : ResponseCallback<T> {


    override fun onStart() {
        vm?.run {
            if (load) {
                sendState(StateData(LaunchStatus.START.name))
            }
        }
        start()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        failure(t)
        vm?.run {
            sendState(StateData(LaunchStatus.FAILURE.name, t))
        }
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        try {
            if (interceptResponse(response)) {
                vm?.run {
                    sendState(StateData(LaunchStatus.COMPLETE.name))
                }
                return
            }
            val body = response.body()
            body?.run {
                response.headers()
                response(this)
            } ?: onFailure(call, Exception("$call : response.body() is null ."))
            vm?.run {
                sendState(StateData(LaunchStatus.COMPLETE.name))
            }
        } catch (e: Exception) {
            onFailure(call, e)
        }
    }
}


/**
 * @param start  开始网络请求事件处理回调， 当返回true 时表示不再走ViewModel统一处理方法
 * @param complete 网络响应结果事件处理回调， 当返回true 时表示不再走ViewModel统一处理方法
 * @param failure 异常抛出事件处理回调， 当返回true 时表示不再走ViewModel统一处理方法
 */
class HttpLaunchCallBack(
    val start: () -> Boolean = { true },
    val complete: () -> Boolean = { true },
    val failure: (t: Throwable) -> Boolean = { true },
    private val vm: StateViewModel? = null,
) : LaunchCallBack {

    override fun onStart() {
        val intercept = start()
        if (intercept) {
            return
        }
        vm?.run {
            sendState(StateData(LaunchStatus.START.name))
        }

    }

    override fun onFailure(t: Throwable) {
        val intercept = failure(t)
        if (intercept) {
            return
        }
        vm?.run {
            sendState(StateData(LaunchStatus.FAILURE.name, t))
        }
    }

    override fun onComplete() {
        val intercept = complete()
        if (intercept) {
            return
        }
        vm?.run {
            sendState(StateData(LaunchStatus.COMPLETE.name))
        }
    }
}