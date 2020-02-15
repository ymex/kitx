package cn.ymex.kitx.sample.adapter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.ymex.kitx.sample.R
import cn.ymex.kitx.sample.core.MutService
import cn.ymex.kitx.sample.ui.main.MainFragment

class AdapterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adapter_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        startService(Intent(this,MutService::class.java))
    }

}
