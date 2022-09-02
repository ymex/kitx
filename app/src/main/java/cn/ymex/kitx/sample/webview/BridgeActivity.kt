package cn.ymex.kitx.sample.webview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import cn.ymex.kitx.sample.databinding.ActivityBridgeBinding
import cn.ymex.kitx.start.app.ViewBindingActivity
import cn.ymex.kitx.widget.webview.proxy.BridgeWebView
import cn.ymex.kitx.widget.webview.proxy.OnReturnValue


class BridgeActivity : ViewBindingActivity<ActivityBridgeBinding>() {


    override fun viewBinding(): ActivityBridgeBinding {
        return ActivityBridgeBinding.inflate(layoutInflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = ActivityBridgeBinding.inflate(layoutInflater)
        setContentView(vb.root)
        // set debug mode


        var url =
            "https://wawah5.szsget.cn/channel/?mobile=12345678901&code=94654343&nonce_str=77WWCFU09607OTTIDLVYIAV8K&sign=2850EA81FE1F6C0433DC2DC0AD99AF06#/"
        url = "file:///android_asset/js_bridge_test.html"
//        vBrowser.loadUrl("http://wechatfe.github.io/vconsole/demo.html")
        vb.vBrowser.loadUrl(url)



    }
}