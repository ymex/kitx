package cn.ymex.kitx.sample.permission

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.ymex.kitx.core.permission.PermissionRequest
import cn.ymex.kitx.sample.R
import cn.ymex.kitx.sample.databinding.PermissionActivityBinding

class PermissionActivity : AppCompatActivity() {
    lateinit var vb : PermissionActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = PermissionActivityBinding.inflate(layoutInflater)
        setContentView(vb.root)


        vb.btnRequest.setOnClickListener {
            PermissionRequest(this).request(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.REQUEST_INSTALL_PACKAGES,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                println("-------permission request result: $it")
                true
            }
        }


        vb.btnRequestEach.setOnClickListener {
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

        vb.btnBind.setOnClickListener {
            PermissionRequest(this).requestEach(
                arrayOf(
                    Manifest.permission.CAMERA,Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE
                )
            ) {
                println("-------permission request result: $it")
                //返回true 表示 处理完一个请求 将继续请求下一个。直到全部请求
                true
            }
        }
    }

}
