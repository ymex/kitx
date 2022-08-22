package cn.ymex.kitx.anhttp.lifecycle


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import retrofit2.Call


open class LifeViewModel : ViewModel() {
    private val calls: MutableList<Call<*>> = mutableListOf()
    private val jobs :MutableList<Job> = mutableListOf()
    fun <T> put(call: Call<T>) {
        if (!calls.contains(call)) {
            calls.add(call)
        }
    }

    fun put(job:Job){
        if (!jobs.contains(job)){
            jobs.add(job)
        }
    }

    override fun onCleared() {
        super.onCleared()
        jobs.forEach {
            if (it.isActive){
                it.cancel(null)
            }
        }
        calls.forEach {
            if (!it.isCanceled) {
                it.cancel()
            }
        }
    }
}