package cn.ymex.kitx.anhttp.lifecycle


import androidx.lifecycle.ViewModel
import retrofit2.Call


open class LifeViewModel : ViewModel() {
    private val calls: MutableList<Call<*>> = mutableListOf()
    fun <T> put(call: Call<T>) {
        if (!calls.contains(call)) {
            calls.add(call)
        }
    }

    override fun onCleared() {
        super.onCleared()
        calls.forEach {
            if (!it.isCanceled) {
                it.cancel()
            }
        }
    }
}