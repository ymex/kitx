package cn.ymex.tps.umeng

import android.content.Intent
import android.os.Bundle
import com.umeng.message.UmengNotificationClickHandler
import com.umeng.message.UmengNotifyClickActivity
import com.umeng.message.entity.UMessage
import org.android.agoo.common.AgooConstants
import org.json.JSONObject

open class PushHoldActivity : UmengNotifyClickActivity() {
    override fun onCreate(p0: Bundle?) {
        super.onCreate(p0)
    }
    override fun onMessage(intent: Intent) {
        super.onMessage(intent)
        val body = intent.getStringExtra(AgooConstants.MESSAGE_BODY)
        try {
            val deal = UmengManager.instance.messageHandler?.dealWithNativeMessage(this,
                PushMessage(UMessage(JSONObject(body)))
            )?:true
            runOnUiThread {
                if (deal){
                    val message = UMessage(JSONObject(body))
                    message.clickOrDismiss = true
                    UmengNotificationClickHandler().handleMessage(this,message)
                }
                finish()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}