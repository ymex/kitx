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
import cn.ymex.kitx.sample.permission.PermissionActivity
import cn.ymex.kitx.utils.itemDecorationDrawable
import cn.ymex.kitx.utils.todip
import cn.ymex.kitx.utils.verticalItemDecoration
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        vRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        vRecycler.addItemDecoration(
            verticalItemDecoration(
                this,
                itemDecorationDrawable(height = 2.todip().toInt())
            )
        )
        val delegateAdapter = DelegateAdapter.create()
        delegateAdapter.bind(ActInt::class.java, BinderItemAction())
        delegateAdapter.attachRecyclerView(vRecycler)
        SharedPreferences.put("k_v",System.currentTimeMillis())
        delegateAdapter.data = mutableListOf(
            ActInt("pure adapter") {
                val intent = Intent(this, AdapterActivity::class.java)
                startActivity(intent)
            },
            ActInt("Permission request"){
                val intent = Intent(this, PermissionActivity::class.java)
                startActivity(intent)
            }) as List<Any>?
    }

}
