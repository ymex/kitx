package cn.ymex.kitx.sample.permission

import android.Manifest
import android.os.Bundle
import android.view.View
import cn.ymex.kitx.core.permission.PermissionRequest
import cn.ymex.kitx.sample.databinding.PermissionActivityBinding
import cn.ymex.kitx.start.app.ViewBindingActivity
import android.content.Intent

import android.content.DialogInterface
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog


class PermissionActivity : ViewBindingActivity<PermissionActivityBinding>() {

    override fun viewBinding() = PermissionActivityBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    Manifest.permission.CAMERA,
                    Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE
                )
            ) {
                println("-------permission request result: $it")
                //返回true 表示 处理完一个请求 将继续请求下一个。直到全部请求
                true
            }
        }
    }


    private fun showWaringDialog() {
       AlertDialog.Builder(this)
            .setTitle("警告！")
            .setMessage("请前往设置->应用->权限中打开相关权限，否则功能无法正常运行！")
            .setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
                dialog.dismiss()
            }).show()
    }

}
