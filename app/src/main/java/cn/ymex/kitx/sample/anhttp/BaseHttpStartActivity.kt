package cn.ymex.kitx.sample.anhttp

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import cn.ymex.kitx.anhttp.lifecycle.StateViewModel
import cn.ymex.kitx.anhttp.lifecycle.ToastViewModel
import cn.ymex.kitx.anhttp.lifecycle.ViewStatus
import cn.ymex.kitx.sample.R
import cn.ymex.kitx.start.app.StartActivity
import cn.ymex.kitx.start.app.ViewBindingActivity
import cn.ymex.kitx.tips.view.find
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

        if (viewModel is ToastViewModel){
            viewModel.toaster.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })

        }
    }
}