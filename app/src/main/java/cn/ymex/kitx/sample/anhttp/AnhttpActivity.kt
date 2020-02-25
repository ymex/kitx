package cn.ymex.kitx.sample.anhttp

import android.os.Bundle
import android.view.View
import cn.ymex.kitx.core.app.AppActivity
import cn.ymex.kitx.sample.R
import kotlinx.android.synthetic.main.activity_anhttp.*

/**
 * Created by ymex on 2020/2/25.
 * About:
 */
class AnhttpActivity : AppActivity() {
    override fun onCreateView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_anhttp
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vRefresh.setOnRefreshListener {

        }

        vRefresh.setOnLoadMoreListener {

        }

        btnHttp.setOnClickListener {
            vState.showView(R.layout.layout_state_load)
            println("------------click")

        }
    }


    fun finishRefreshLoadMore() {
        vRefresh.finishRefresh()
        vRefresh.finishLoadMore()
        vState.showContentView()
    }
}