package cn.ymex.kitx.sample.adapter

import android.content.Intent
import android.os.Bundle
import android.view.View
import cn.ymex.kitx.sample.R
import cn.ymex.kitx.sample.core.MutService
import cn.ymex.kitx.sample.databinding.AdapterActivityBinding
import cn.ymex.kitx.sample.ui.main.MainFragment
import cn.ymex.kitx.start.app.ViewBindingActivity

class AdapterActivity : ViewBindingActivity<AdapterActivityBinding>() {

    override fun viewBinding() = AdapterActivityBinding.inflate(layoutInflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        startService(Intent(this, MutService::class.java))
    }

}
