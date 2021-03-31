package cn.ymex.kitx.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import cn.ymex.kitx.core.adapter.recycler.DelegateAdapter
import cn.ymex.kitx.core.storage.SharedPreferences
import cn.ymex.kitx.sample.adapter.ActInt
import cn.ymex.kitx.sample.adapter.AdapterActivity
import cn.ymex.kitx.sample.adapter.BinderItemAction
import cn.ymex.kitx.sample.anhttp.AnhttpStartActivity
import cn.ymex.kitx.sample.databinding.MainActivityBinding
import cn.ymex.kitx.sample.permission.PermissionActivity
import cn.ymex.kitx.sample.updater.UpdaterActivity
import cn.ymex.kitx.sample.webview.BridgeActivity
import cn.ymex.kitx.tips.view.dp
import cn.ymex.kitx.tips.view.itemDecorationDrawable
import cn.ymex.kitx.tips.view.px
import cn.ymex.kitx.tips.view.verticalItemDecoration


class MainActivity : AppCompatActivity() {

    lateinit var bv :MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bv = MainActivityBinding.inflate(layoutInflater)
        setContentView(bv.root)
        bv.vRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bv.vRecycler.addItemDecoration(
            verticalItemDecoration(
                this,
                itemDecorationDrawable(height = 2.px.toInt())
            )
        )
        val delegateAdapter = DelegateAdapter.create()
        delegateAdapter.bind(ActInt::class.java, BinderItemAction())
        delegateAdapter.attachRecyclerView(bv.vRecycler)
        SharedPreferences.put("k_v", System.currentTimeMillis())
        delegateAdapter.data = mutableListOf(
            ActInt("pure adapter") {
                val intent = Intent(this, AdapterActivity::class.java)
                startActivity(intent)
            },
            ActInt("Permission request") {
                val intent = Intent(this, PermissionActivity::class.java)
                startActivity(intent)
            }, ActInt("WebView") {
                val intent = Intent(this, BridgeActivity::class.java)
                startActivity(intent)
            }, ActInt("Http Request") {
                val intent = Intent(this, AnhttpStartActivity::class.java)
                startActivity(intent)
            }, ActInt("update version") {
                val intent = Intent(this, UpdaterActivity::class.java)
                startActivity(intent)
            }
        ) as List<Any>?
    }

}
