package cn.ymex.kitx.start.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import cn.ymex.kitx.core.app.ViewModelActivity


abstract class ViewBindingActivity<T:ViewBinding>:ViewModelActivity() {

    protected lateinit var vb:T

    abstract fun viewBinding():T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = viewBinding()
        return vb.root
    }
}