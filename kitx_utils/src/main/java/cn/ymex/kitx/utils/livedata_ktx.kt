package cn.ymex.kitx.utils

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(@NonNull owner: LifecycleOwner, block:(T)->Unit) {
    this.observe(owner, Observer {
        block(it)
    })
}
