package cn.ymex.kitx.sample.updater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.ymex.kitx.sample.databinding.ActivityUpdaterBinding
import cn.ymex.kitx.start.app.StartActivity
import cn.ymex.kitx.start.updater.UpdateConfig
import cn.ymex.kitx.start.updater.VersionDialogFragment

class UpdaterActivity :StartActivity() {

    lateinit var vb : ActivityUpdaterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = ActivityUpdaterBinding.inflate(inflater)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.btnVersion.setOnClickListener {
            VersionDialogFragment.show(supportFragmentManager)
        }

        vb.btnUpdate.setOnClickListener {
            var url = "https://4d65fd13ac20f0c1aa6a4f3fa0c110bc.dlied1.cdntips.net/dlied1.qq.com/qqweb/QQlite/Android_apk/qqlite_4.0.1.1060_537064364.apk"
            val updateConfig = UpdateConfig(url,"QQ.apk","更新的内容：\n1、fix bug \n2、优化性能",forceUpdate = false)
            VersionDialogFragment.show(supportFragmentManager,updateConfig)

        }
    }


}