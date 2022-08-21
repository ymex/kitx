package cn.ymex.kitx.anhttp

/**
 * 协和处理回调
 */
interface LaunchCallBack{
    /**
     * 开始处理
     */
    fun onStart()

    /**
     * 处理中有异常
     */
    fun onFailure(t : Throwable)

    /**
     * 协和处理开异常结束、若有异常则不会进入onComplete
     */
    fun onComplete()
}


