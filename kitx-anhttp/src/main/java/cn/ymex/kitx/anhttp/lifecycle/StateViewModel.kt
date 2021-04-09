package cn.ymex.kitx.anhttp.lifecycle

import android.os.Bundle
import androidx.lifecycle.MutableLiveData


/**
 * 视图状态
 */
enum class ViewStatus {
    EMPTY, //空数据状态
    NORMAL, //正常状态
    LOADING, //加载中
    ERR //一般错误
}

data class PageState(var status: ViewStatus = ViewStatus.NORMAL, var throwable: Throwable? = null,var bundle: Bundle = Bundle.EMPTY) {
    override fun toString(): String {
        return "PageState(status=$status, message='$throwable', bundle='${bundle}')"
    }
}

/**
 * 状态通知
 */
open class StateViewModel : LifeViewModel() {
    private val _stateLiveData = MutableLiveData<PageState>()
    val stater = _stateLiveData

    fun state(pageState: PageState){
        stater.postValue(pageState)
    }
}