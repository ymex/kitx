package cn.ymex.kitx.sample.anhttp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import cn.ymex.kitx.core.app.AppActivity
import cn.ymex.kitx.sample.R
import cn.ymex.kitx.sample.anhttp.repository.viewmodel.LoginVMFactory
import cn.ymex.kitx.sample.anhttp.repository.viewmodel.LoginViewModel
import cn.ymex.kitx.utils.setOnClickThrottleListener
import kotlinx.android.synthetic.main.activity_anhttp.*


class AnhttpActivity : AppActivity() {

    private val loginViewModel: LoginViewModel by viewModels { LoginVMFactory }

    override fun getViewModels(): MutableList<ViewModel> {
        return mutableListOf(loginViewModel)
    }



    override fun onCreateView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_anhttp
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vRefresh.setOnRefreshListener {

        }

        vRefresh.setOnLoadMoreListener {

        }

        btnHttp.setOnClickThrottleListener {
            
        }
    }



    fun currentThread() {
        println("-------thread:" + Thread.currentThread().name + "   " + Thread.currentThread().id)
    }
//    vState.showView(R.layout.layout_state_load)

    fun finishRefreshLoadMore() {
        vRefresh.finishRefresh()
        vRefresh.finishLoadMore()
        vState.showContentView()
    }
}