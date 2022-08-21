package cn.ymex.kitx.sample.anhttp

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import cn.ymex.kitx.anhttp.lifecycle.StateViewModel
import cn.ymex.kitx.anhttp.lifecycle.WarnViewModel
import cn.ymex.kitx.anhttp.lifecycle.LaunchStatus
import cn.ymex.kitx.sample.R
import cn.ymex.kitx.start.app.ViewBindingActivity
import cn.ymex.kitx.snippet.view.find
import cn.ymex.kitx.widget.state.StateConstraintLayout

/**
 * Created by ymex on 2020/2/26.
 * About:
 */
abstract class BaseHttpStartActivity<T:ViewBinding> : ViewBindingActivity<T>() {

    lateinit var vState: StateConstraintLayout

    override fun setCommonObserver(viewModel: ViewModel) {
        super.setCommonObserver(viewModel)

        vState = view.find(R.id.vState)

        if (viewModel is StateViewModel) {

            viewModel.stater.observe(this, Observer {

                when (it.status) {
                    LaunchStatus.START.name -> {
                        vState.showView(R.layout.layout_state_load)
                    }
                    LaunchStatus.COMPLETE.name -> {
                        if (vState.currentLayoutId == R.layout.layout_state_load) {
                            vState.showContentView()
                        }
                    }
                    LaunchStatus.FAILURE.name -> {
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

        if (viewModel is WarnViewModel){
            viewModel.toaster.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })

        }
    }
}