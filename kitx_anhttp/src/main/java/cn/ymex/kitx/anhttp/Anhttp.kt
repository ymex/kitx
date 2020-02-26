package cn.ymex.kitx.anhttp

import cn.ymex.kitx.anhttp.lifecycle.LifeViewModel
import retrofit2.Call
import retrofit2.Retrofit


fun anHttp(retrofit: Retrofit) {
    AnHttpManager.init(retrofit)
}

inline fun <reified T> createRetrofitService(): T {
    return AnHttpManager.instance.getRetrofit().create(T::class.java)
}


/**
 * create request
 */
inline fun <reified T, R> anHttpRequest(
    block: (service: T) -> Call<R>, callback: ResponseCallback<R>
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
    val call = block()
    call.enqueue(callback)
}


/**
 * create request
 */
inline fun <reified T, R> LifeViewModel.anHttpRequest(
    block: (service: T) -> Call<R>, callback: ResponseCallback<R>
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
    val call = block()
    put(call)
    call.enqueue(callback)
}


/**
 * http Response callback
 */
fun <T> anHttpResponse(
    response: (data: T?) -> Unit,
    failure: (t: Throwable) -> Unit,
    start: () -> Unit
): HttpResponse<T> {
    return HttpResponse(response, failure, start)
}


fun <T> anHttpResponse(
    response: (data: T?) -> Unit,
    failure: (t: Throwable) -> Unit
): HttpResponse<T> {
    return anHttpResponse(response, failure, {})
}

fun <T> anHttpResponse(
    response: (data: T?) -> Unit
): HttpResponse<T> {
    return anHttpResponse(response, {}, {})
}