package cn.ymex.kitx.anhttp

interface LaunchCallBack{
    fun onStart()
    fun onFailure(t : Throwable)
    fun onComplete()
}


