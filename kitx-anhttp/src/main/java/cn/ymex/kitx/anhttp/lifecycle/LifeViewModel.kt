package cn.ymex.kitx.anhttp.lifecycle


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import retrofit2.Call


open class LifeViewModel : ViewModel() {
    private val calls: MutableList<Call<*>> = mutableListOf()
    private val jobs :MutableList<Job> = mutableListOf()

    var autoClear = true //自动停止并回收


    fun <T> put(call: Call<T>) {
        if (!calls.contains(call)) {
            calls.add(call)
        }
    }

    fun put(job:Job){
        if (!jobs.contains(job)){
            job.hashCode()
            jobs.add(job)
        }
    }

    fun clear(job: Job){
        if (jobs.contains(job) && job.isActive) {
            job.cancel(null)
        }
    }

    fun <T> clear(call: Call<T>) {
        if (calls.contains(call) && !call.isCanceled) {
            call.cancel()
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (autoClear){
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
            calls.clear()
            jobs.clear()
        }
    }
}