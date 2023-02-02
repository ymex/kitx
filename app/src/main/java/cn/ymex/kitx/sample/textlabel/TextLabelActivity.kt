package cn.ymex.kitx.sample.textlabel

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import cn.ymex.kitx.sample.R
import cn.ymex.kitx.sample.databinding.ActivityTextLabelBinding
import cn.ymex.kitx.start.app.ViewBindingActivity
import cn.ymex.kitx.widget.label.ImageSpannable
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
        dear()
    }

    private fun dear(){
        val context: Context = this
        val forgimg = ImageSpannable(context, R.mipmap.frog)
        val span1 = SpanCell.build().textColor(Color.parseColor("#008577")).text("一只小青蛙").imageSpanInLast(true).imageSpan(forgimg)
        val deerimg = ImageSpannable(context, R.mipmap.deer)
        val span2 = SpanCell.build().textColor(Color.parseColor("#FF9800")).text(",发现了一只受伤的小鹿").imageSpan(deerimg).imageSpanInLast(true)
        val hippoimg = ImageSpannable(context, R.mipmap.hippo, ImageSpannable.ALIGN_FONTCENTER)
        hippoimg.setSize(64, 64)
        val span3 = SpanCell.build().text("于是它去寻求小牛").imageSpanInLast(true).imageSpan(hippoimg)

        val owlimg = ImageSpannable(context, R.mipmap.owl, ImageSpannable.ALIGN_FONTCENTER)
        owlimg.setSize(160, 160)
        val span4 = SpanCell.build().imageSpanInLast(true)
            .text("的帮助。小牛说，不帮不帮就不帮。。于是小青蛙又去向其他 动物寻求帮助。于是它找到了猫头鹰").imageSpan(owlimg)
        val span5 = SpanCell.build().text(",于是他们一起愉快的喝可乐 ！呵呵")
        vb.tvTextImage.setText(span1, span2, span3, span4, span5)
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