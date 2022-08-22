package cn.ymex.kitx.sample.anhttp

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import cn.ymex.kitx.anhttp.LaunchException
import cn.ymex.kitx.anhttp.lifecycle.ActionViewModel
import cn.ymex.kitx.anhttp.lifecycle.LaunchStatus
import cn.ymex.kitx.anhttp.lifecycle.StateViewModel
import cn.ymex.kitx.sample.R
import cn.ymex.kitx.snippet.view.find
import cn.ymex.kitx.start.app.ViewBindingActivity
import cn.ymex.kitx.widget.state.StateConstraintLayout
import retrofit2.HttpException

/**
 * Created by ymex on 2020/2/26.
 * About:
 */
abstract class BaseHttpStartActivity<T : ViewBinding> : ViewBindingActivity<T>() {

    lateinit var vState: StateConstraintLayout

    fun setAnHttpObserver(viewModel: ViewModel) {

        vState = view.find(R.id.vState)

        if (viewModel is StateViewModel) {

            viewModel.stater.observe(this, Observer {

                when (it.status) {
                    LaunchStatus.START -> {
                        vState.showView(R.layout.layout_state_load)
                    }
                    LaunchStatus.COMPLETE -> {
                        if (vState.currentLayoutId == R.layout.layout_state_load) {
                            vState.showContentView()
                        }
                    }
                    LaunchStatus.FAILURE -> {
                        if (vState.currentLayoutId == R.layout.layout_state_load) {
                            vState.showContentView()
                        }

                        if (it.throwable is LaunchException) {
                            //自定义异常处理
                            Toast.makeText(
                                this, "LaunchException:${it.throwable?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (it.throwable is HttpException) {
                            //网络请求异常 ， 如404 ， 500  http code 不为200时retrofit2 则视为异常。

                            val errorString =
                                (it.throwable as HttpException).response()?.errorBody()?.string()
                                    ?: it.throwable?.message

                            Toast.makeText(
                                this, "HttpException:${errorString}",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this,
                                it.throwable?.localizedMessage ?: "err",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    }
                }
            })

        }
        if (viewModel is ActionViewModel) {
            viewModel.toaster.observe(this, Observer {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
        }

    }
}