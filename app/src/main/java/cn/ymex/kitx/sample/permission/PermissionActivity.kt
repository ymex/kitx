package cn.ymex.kitx.sample.permission

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.ymex.kitx.core.permission.PermissionRequest
import cn.ymex.kitx.sample.R
import kotlinx.android.synthetic.main.permission_activity.*

class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.permission_activity)


        btnRequest.setOnClickListener {
            PermissionRequest(this).request(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                println("-------permission request result: $it")
                true
            }
        }


        btnRequestEach.setOnClickListener {
            PermissionRequest(this).requestEach(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.REQUEST_INSTALL_PACKAGES,
                    Manifest.permission.READ_PHONE_STATE
                )
            ) {
                println("-------permission request result: $it")
                //返回true 表示 处理完一个请求 将继续请求下一个。直到全部请求
                true
            }
        }
    }

}
