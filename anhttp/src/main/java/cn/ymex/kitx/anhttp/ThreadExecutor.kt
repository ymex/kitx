package cn.ymex.kitx.anhttp

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

class ThreadExecutor : Executor {
    private val handler = Handler(Looper.getMainLooper())
    override fun execute(runnable: Runnable) {
        runnable.run()
    }

    fun executeMain(runnable: Runnable) {
        handler.post(runnable)
    }
}
