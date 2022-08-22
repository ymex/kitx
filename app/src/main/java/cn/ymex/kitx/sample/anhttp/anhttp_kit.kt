package cn.ymex.kitx.sample.anhttp

import cn.ymex.kitx.anhttp.LaunchCallBack
import cn.ymex.kitx.anhttp.launch
import cn.ymex.kitx.anhttp.lifecycle.StateViewModel
import cn.ymex.kitx.anhttp.simpleLaunchCallBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job


fun StateViewModel.stateLaunch(
    loading:Boolean = false,
    block: suspend CoroutineScope.() -> Unit
):Job{
    val callback: LaunchCallBack = simpleLaunchCallBack(
        start = {
            if (loading){
                sendStartState()
            }
        },
        complete = {
            if (loading){
                sendCompleteState()
            }
        },
        failure = {
            sendFailureState(it)
        }
    )
    return launch(callback, block)
}