package cn.ymex.kitx.anhttp

import cn.ymex.kitx.anhttp.lifecycle.LifeViewModel
import cn.ymex.kitx.anhttp.lifecycle.StateViewModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

/**
 * urs:
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

//inline fun <reified T, R> anHttpRequest(
//    block: (service: T) -> Call<R>,
//    callback: ResponseCallback<R>
//) {
//    anHttpRequest(callback, block)
//}
//
//fun <R> anHttpRequest(
//    block: () -> Call<R>,
//    callback: ResponseCallback<R>
//) {
//    anHttpRequest(callback, block)
//}

//- - - - - - -

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

//inline fun <reified T, R> LifeViewModel.anHttpRequest(
//    block: (service: T) -> Call<R>,
//    callback: ResponseCallback<R>
//) {
//    this.anHttpRequest(callback, block)
//}
//
//
//fun <R> LifeViewModel.anHttpRequest(
//    block: () -> Call<R>,
//    callback: ResponseCallback<R>
//) {
//    this.anHttpRequest(callback, block)
//}

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

///////////////StateViewModel

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


//----新增非数据

//--------------------------------------------------start
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
//==================================================end
//--------------------------------------------------start

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

