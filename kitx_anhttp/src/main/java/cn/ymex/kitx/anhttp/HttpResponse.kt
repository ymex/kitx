package cn.ymex.kitx.anhttp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ResponseCallback<T> : Callback<T> {
    fun onStart()
}

class HttpResponse<T>(
    val response: (data: T?) -> Unit,
    val failure: (t: Throwable) -> Unit,
    val start: () -> Unit
) : ResponseCallback<T> {
    override fun onStart() {
        start()
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        failure(t)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        try {
            response(response.body())
        } catch (e: Exception) {
            onFailure(call, e)
        }
    }
}