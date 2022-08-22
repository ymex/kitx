package cn.ymex.kitx.anhttp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.ymex.kitx.anhttp.lifecycle.LifeViewModel
import cn.ymex.kitx.anhttp.lifecycle.StateViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

/**
 * use:
 * anHttp(AnHttpManager.newRetrofitBuilder(baseUrl = "http://xxxxx.com/").build())
 */

fun anHttp(retrofit: Retrofit) {
    AnHttpManager.init(retrofit)
}

inline fun <reified T> createRetrofitService(): T {
    return AnHttpManager.instance.getRetrofit().create(T::class.java)
}


//------------------------anHttpRequest- create request----------------

/**
 * create request
 *
 */
inline fun <reified T, R> anHttpRequest(
    block: (service: T) -> Call<R>,
    callback: ResponseCallback<R>
) {
    ThreadExecutor().executeMain(Runnable {
        callback.onStart()
    })
    val call = block(createRetrofitService())
    call.enqueue(callback)
}

fun <R> anHttpRequest(
    block: () -> Call<R>,
    callback: ResponseCallback<R>
) {
    ThreadExecutor().executeMain(Runnable {
        callback.onStart()
    })
    val call = block()
    call.enqueue(callback)
}


inline fun <reified T, R> LifeViewModel.anHttpRequest(
    block: (service: T) -> Call<R>,
    callback: ResponseCallback<R>
) {
    ThreadExecutor().executeMain(Runnable {
        callback.onStart()
    })
    val call = block(createRetrofitService())
    put(call)
    call.enqueue(callback)
}


fun <R> LifeViewModel.anHttpRequest(
    block: () -> Call<R>,
    callback: ResponseCallback<R>
) {
    ThreadExecutor().executeMain(Runnable {
        callback.onStart()
    })
    val call = block()
    put(call)
    call.enqueue(callback)
}


//------------------------anHttpResponse- http Response callback----------------

fun <T> anHttpResponse(
    loading: Boolean = true,
    response: (data: T) -> Unit,
    failure: (t: Throwable) -> Unit,
    start: () -> Unit
): HttpResponse<T> {
    return HttpResponse(response, failure, start, { false }, null, loading)
}


fun <T> anHttpResponse(
    response: (data: T) -> Unit,
    failure: (t: Throwable) -> Unit,
    start: () -> Unit
): HttpResponse<T> {
    return HttpResponse(response, failure, start, { false }, null, true)
}

fun <T> anHttpResponse(
    loading: Boolean = true,
    response: (data: T) -> Unit,
    failure: (t: Throwable) -> Unit
): HttpResponse<T> {
    return HttpResponse(response, failure, {}, { false }, null, loading)
}

fun <T> anHttpResponse(
    response: (data: T) -> Unit,
    failure: (t: Throwable) -> Unit
): HttpResponse<T> {
    return HttpResponse(response, failure, {}, { false }, null, true)
}


fun <T> anHttpResponse(
    loading: Boolean = true,
    response: (data: T) -> Unit
): HttpResponse<T> {
    return HttpResponse(response, {}, {}, { false }, null, loading)
}

fun <T> anHttpResponse(
    response: (data: T) -> Unit
): HttpResponse<T> {
    return HttpResponse(response, {}, {}, { false }, null, true)
}

//--------------------------------------------------StateViewModel.anHttpResponse

fun <T> StateViewModel.anHttpResponse(
    loading: Boolean = true,
    response: (data: T) -> Unit,
    failure: (t: Throwable) -> Unit,
    start: () -> Unit
): HttpResponse<T> {
    return HttpResponse(response, failure, start, { false }, this, loading)
}

fun <T> StateViewModel.anHttpResponse(
    response: (data: T) -> Unit,
    failure: (t: Throwable) -> Unit,
    start: () -> Unit
): HttpResponse<T> {
    return HttpResponse(response, failure, start, { false }, this, true)
}


fun <T> StateViewModel.anHttpResponse(
    loading: Boolean = true,
    response: (data: T) -> Unit,
    failure: (t: Throwable) -> Unit
): HttpResponse<T> {
    return HttpResponse(response, failure, {}, { false }, this, loading)
}


fun <T> StateViewModel.anHttpResponse(
    response: (data: T) -> Unit,
    failure: (t: Throwable) -> Unit
): HttpResponse<T> {
    return HttpResponse(response, failure, {}, { false }, this, true)
}


fun <T> StateViewModel.anHttpResponse(
    loading: Boolean = true,
    response: (data: T) -> Unit
): HttpResponse<T> {
    return HttpResponse(response, {}, {}, { false }, this, loading)
}


fun <T> StateViewModel.anHttpResponse(
    response: (data: T) -> Unit
): HttpResponse<T> {
    return HttpResponse(response, {}, {}, { false }, this, true)
}


//--------------------------------------------------anHttpRawResponse

fun <T> anHttpRawResponse(
    loading: Boolean = true,
    response: (data: Response<T>) -> Boolean,
    failure: (t: Throwable) -> Unit,
    start: () -> Unit
): HttpResponse<T> {
    return HttpResponse({}, failure, start, response, null, loading)
}

fun <T> anHttpRawResponse(
    response: (data: Response<T>) -> Boolean,
    failure: (t: Throwable) -> Unit,
    start: () -> Unit
): HttpResponse<T> {
    return HttpResponse({}, failure, start, response, null, true)
}


fun <T> anHttpRawResponse(
    loading: Boolean = true,
    response: (data: Response<T>) -> Boolean,
    failure: (t: Throwable) -> Unit
): HttpResponse<T> {
    return HttpResponse({}, failure, {}, response, null, loading)
}


fun <T> anHttpRawResponse(
    response: (data: Response<T>) -> Boolean,
    failure: (t: Throwable) -> Unit
): HttpResponse<T> {
    return HttpResponse({}, failure, {}, response, null, true)
}


fun <T> anHttpRawResponse(
    loading: Boolean = true,
    response: (data: Response<T>) -> Boolean
): HttpResponse<T> {
    return HttpResponse({}, {}, {}, response, null, loading)
}


fun <T> anHttpRawResponse(
    response: (data: Response<T>) -> Boolean
): HttpResponse<T> {
    return HttpResponse({}, {}, {}, response, null, true)
}


//--------------------------------------------------StateViewModel.anHttpRawResponse

fun <T> StateViewModel.anHttpRawResponse(
    loading: Boolean = true,
    response: (data: Response<T>) -> Boolean,
    failure: (t: Throwable) -> Unit,
    start: () -> Unit
): HttpResponse<T> {
    return HttpResponse({}, failure, start, response, this, loading)
}

fun <T> StateViewModel.anHttpRawResponse(
    response: (data: Response<T>) -> Boolean,
    failure: (t: Throwable) -> Unit,
    start: () -> Unit
): HttpResponse<T> {
    return HttpResponse({}, failure, start, response, this, true)
}


fun <T> StateViewModel.anHttpRawResponse(
    loading: Boolean = true,
    response: (data: Response<T>) -> Boolean,
    failure: (t: Throwable) -> Unit
): HttpResponse<T> {
    return HttpResponse({}, failure, {}, response, this, loading)
}


fun <T> StateViewModel.anHttpRawResponse(
    response: (data: Response<T>) -> Boolean,
    failure: (t: Throwable) -> Unit
): HttpResponse<T> {
    return HttpResponse({}, failure, {}, response, this, true)
}


fun <T> StateViewModel.anHttpRawResponse(
    loading: Boolean = true,
    response: (data: Response<T>) -> Boolean
): HttpResponse<T> {
    return HttpResponse({}, {}, {}, response, this, loading)
}


fun <T> StateViewModel.anHttpRawResponse(
    response: (data: Response<T>) -> Boolean
): HttpResponse<T> {
    return HttpResponse({}, {}, {}, response, this, true)
}


//--------------------------------------------------协程处理

fun ViewModel.simpleLaunchCallBack(
    start: () -> Unit = { },
    complete: () -> Unit = { },
    failure: (t: Throwable) -> Unit = { }
): StateLaunchCallBack {
    return StateLaunchCallBack(start, complete, failure)
}


fun ViewModel.launch(
    callback: LaunchCallBack? = null,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val job = viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
        callback?.run {
            throwable.printStackTrace()//处理异常
            onFailure(throwable)
        }
    }) {
        callback?.run {
            onStart()
        }
        block.invoke(this)
        callback?.run {
            onComplete()
        }
    }
    if (this is LifeViewModel) {
        this.put(job)
    }
    return job
}

fun ViewModel.launch(
    failure: (Throwable) -> Unit,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return launch(simpleLaunchCallBack(failure = failure), block)
}

/**
 * 发起协程网络请求
 */
fun StateViewModel.httpLaunch(
    block: suspend CoroutineScope.() -> Unit
): Job {
    val callback:LaunchCallBack = simpleLaunchCallBack(
        start = {
            sendStartState()
        },
        complete = {
            sendCompleteState()
        },
        failure = {
            sendFailureState(it)
        }
    )
    return launch(callback, block)
}