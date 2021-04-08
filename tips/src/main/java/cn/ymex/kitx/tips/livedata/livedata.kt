package cn.ymex.kitx.tips.livedata

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * 语法糖 ： LiveData 订阅
 */
fun <T> LiveData<T>.observe(@NonNull owner: LifecycleOwner, block:(T)->Unit) {
    this.observe(owner, Observer {
        block(it)
    })
}
