package cn.ymex.kitx.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import cn.ymex.kitx.core.adapter.recycler.DelegateAdapter
import cn.ymex.kitx.core.storage.SharedPreferences
import cn.ymex.kitx.sample.adapter.ActInt
import cn.ymex.kitx.sample.adapter.AdapterActivity
import cn.ymex.kitx.sample.adapter.BinderItemAction
import cn.ymex.kitx.sample.anhttp.AnhttpStartActivity
import cn.ymex.kitx.sample.databinding.MainActivityBinding
import cn.ymex.kitx.sample.permission.PermissionActivity
import cn.ymex.kitx.sample.umeng.UmengActivity
import cn.ymex.kitx.sample.updater.UpdaterActivity
import cn.ymex.kitx.sample.webview.BridgeActivity
import cn.ymex.kitx.start.app.ViewBindingActivity
import cn.ymex.kitx.snippet.view.itemDecorationDrawable
import cn.ymex.kitx.snippet.view.px
import cn.ymex.kitx.snippet.view.verticalItemDecoration


class MainActivity : ViewBindingActivity<MainActivityBinding>() {

    override fun viewBinding() = MainActivityBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.vRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        vb.vRecycler.addItemDecoration(
            verticalItemDecoration(
                this,
                itemDecorationDrawable(height = 2.px.toInt())
            )
        )
        val delegateAdapter = DelegateAdapter.create()
        delegateAdapter.bind(ActInt::class.java, BinderItemAction())
        delegateAdapter.attachRecyclerView(vb.vRecycler)
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
//            , ActInt("umeng") {
//                val intent = Intent(this, UmengActivity::class.java)
//                startActivity(intent)
//            },
        ) as List<Any>?
    }

}
