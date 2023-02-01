package cn.ymex.kitx.sample.textlabel

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.ymex.kitx.sample.databinding.ActivityTextLabelBinding
import cn.ymex.kitx.sample.databinding.ActivityUpdaterBinding
import cn.ymex.kitx.start.app.ViewBindingActivity
import cn.ymex.kitx.widget.label.SpanCell

class TextLabelActivity : ViewBindingActivity<ActivityTextLabelBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun viewBinding(): ActivityTextLabelBinding {
        return ActivityTextLabelBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.tvLicenseGuideClick.run {
            //您可通过阅读完整的《九目妖APP用户使用协议》和《九目妖APP隐私政策》来了解详细信息。

            val userLicenseSpan = SpanCell.build()
                .textColor(Color.parseColor("#887acc"))
                .text("《九目妖APP用户使用协议》")

            userLicenseSpan.setClickableSpan { view, spanCell ->
                println("---------------it:${spanCell.text}")
            }
            val privateLicenseSpan = SpanCell.build()
                .textColor(Color.parseColor("#887acc"))
                .text("《九目妖APP隐私政策》")

            privateLicenseSpan.setClickableSpan { view, spanCell ->
                println("---------------it:${spanCell.text}")
            }
            this.setStartText(SpanCell.build().text("您可通过阅读完整的").textColor(Color.parseColor("#909399")))
            this.setEndText(SpanCell.build().text("来了解详细信息。").textColor(Color.parseColor("#909399")))
//            this.setText(userLicenseSpan, SpanCell.build().text("来了解详细信息。").textColor(Color.parseColor("#909399")))
//            this.setText(
//                SpanCell.build().text("您可通过阅读完整的").textColor(Color.parseColor("#909399")),
//                userLicenseSpan,
//                SpanCell.build().text("和").textColor(Color.parseColor("#909399")),
//                privateLicenseSpan,
//                SpanCell.build().text("来了解详细信息。").textColor(Color.parseColor("#909399")))
        }
    }
}