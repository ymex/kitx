package cn.ymex.kitx.sample.textlabel

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        vb.tvFormatLabel.setOnClickListener {
            vb.tvFormatLabel.text = "3.1415"
            Toast.makeText(this@TextLabelActivity, vb.tvFormatLabel.text, Toast.LENGTH_SHORT).show()

        }
        linkSpanCell()
    }

    private fun linkSpanCell(){
        vb.tvLinkSpan.run {

            val linkColor = Color.parseColor("#6200EE");
            val textColor = Color.parseColor("#909399")

            val userLicenseSpan = SpanCell.build()
                .linkColor(linkColor)
                .textColor(linkColor)
                .text("《APP用户使用协议》")

            userLicenseSpan.setClickableSpan { view, spanCell ->
                Toast.makeText(this@TextLabelActivity, spanCell.text, Toast.LENGTH_SHORT).show()
            }

            val privateLicenseSpan = SpanCell.build()
                .linkColor(linkColor)
                .textColor(linkColor)
                .text("《APP隐私政策》")

            privateLicenseSpan.setClickableSpan { view, spanCell ->
                Toast.makeText(this@TextLabelActivity, spanCell.text, Toast.LENGTH_SHORT).show()
            }

            this.setText(
                SpanCell.build().text("您可通过阅读完整的").textColor(textColor),
                userLicenseSpan,
                SpanCell.build().text("和").textColor(textColor),
                privateLicenseSpan,
                SpanCell.build().text("来了解详细信息。").textColor(textColor)
            )
        }
    }
}