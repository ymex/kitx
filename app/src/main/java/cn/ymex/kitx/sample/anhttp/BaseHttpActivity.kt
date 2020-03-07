package cn.ymex.kitx.sample.anhttp

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import cn.ymex.kitx.anhttp.lifecycle.StateViewModel
import cn.ymex.kitx.anhttp.lifecycle.ViewStatus
import cn.ymex.kitx.sample.R
import cn.ymex.kitx.start.app.Activity
import kotlinx.android.synthetic.main.activity_anhttp.*

/**
 * Created by ymex on 2020/2/26.
 * About:
 */
open class BaseHttpActivity : Activity() {
    override fun setCommonObserver(viewModel: ViewModel) {
        super.setCommonObserver(viewModel)
        if (viewModel is StateViewModel) {
            viewModel.toaster.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })

            viewModel.stater.observe(this, Observer {
                println("-------------life: $it")
                when (it.status) {
                    ViewStatus.LOADING -> {
                        vState.showView(R.layout.layout_state_load)
                    }
                    ViewStatus.NORMAL -> {
                        if (vState.currentLayoutId == R.layout.layout_state_load) {
                            vState.showContentView()
                        }
                    }
                    ViewStatus.ERR -> {
                        Toast.makeText(
                            this,
                            it.throwable?.localizedMessage ?: "err",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                    }
                }
            })

        }
    }
}