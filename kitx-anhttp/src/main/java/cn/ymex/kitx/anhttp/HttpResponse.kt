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
                sendState(StateData(LaunchStatus.START))
            }
        }
        start()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        failure(t)
        vm?.run {
            sendState(StateData(LaunchStatus.FAILURE, t))
        }
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        try {
            if (interceptResponse(response)) {
                vm?.run {
                    sendState(StateData(LaunchStatus.COMPLETE))
                }
                return
            }
            val body = response.body()
            body?.run {
                response.headers()
                response(this)
            } ?: onFailure(call, Exception("$call : response.body() is null ."))
            vm?.run {
                sendState(StateData(LaunchStatus.COMPLETE))
            }
        } catch (e: Exception) {
            onFailure(call, e)
        }
    }
}


/**
 * @param start  开始事件处理回调， 返回结果：是否拦截状态发送。 true 为拦截
 * @param complete 结束事件处理回调， 返回结果：是否拦截状态发送。 true 为拦截
 * @param failure 异常事件处理回调， 返回结果：是否拦截状态发送。 true 为拦截
 */
class StateLaunchCallBack(
    val start: () -> Unit = {  },
    val complete: () -> Unit = {  },
    val failure: (t: Throwable) -> Unit = {  },
) : LaunchCallBack {

    override fun onStart() {
        start()
    }

    override fun onFailure(t: Throwable) {
        failure(t)
    }

    override fun onComplete() {
       complete()
    }
}