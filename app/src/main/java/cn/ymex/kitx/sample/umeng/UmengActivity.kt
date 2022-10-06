package cn.ymex.kitx.sample.umeng

import android.os.Bundle
import android.view.View
import cn.ymex.kitx.sample.databinding.ActivityUmengBinding
import cn.ymex.kitx.start.app.ViewBindingActivity
import cn.ymex.kitx.snippet.context.requestActivity


class UmengActivity : ViewBindingActivity<ActivityUmengBinding>() {

    override fun viewBinding(): ActivityUmengBinding {
        return ActivityUmengBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        println("------utdid:"+requestActivity()?.utdId())
//        vb.tvInfo.text =requestActivity()?.utdId()
    }
}