package cn.ymex.kitx.tps.umeng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;

public class PushActivity extends UmengNotifyClickActivity {
    private static String TAG = PushActivity.class.getName();
    private TextView mipushTextView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.upush_notification);
        mipushTextView = (TextView) findViewById(R.id.notification_title);
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
        final String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Log.i(TAG, body);
        if (!TextUtils.isEmpty(body)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mipushTextView.setText(body);
                }
            });
        }
    }
}
