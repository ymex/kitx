package cn.ymex.kitx.start.updater

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import kotlin.concurrent.thread

class VersionUpdater {


    private var listener: Listener? = null
    var cancel = false
    private var isLoading = false

    fun isLoading(): Boolean {
        return isLoading
    }

    companion object {
        var authorities = ".KitxFilesProvider"
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            VersionUpdater()
        }
    }

    /**
     * @param context
     * @param url apk下载地址
     * @param fileName 保存的文件名
     * @param downloadListener 下载监听
     */
    fun download(
        context: Context,
        url: String,
        fileName: String,
        downloadListener: Listener
    ) {
        if (isLoading) {
            listener?.run { onDownloadFinish(null, "pre download task is running! ") }
            return
        }
        this.listener = downloadListener
        val path = context.filesDir.absolutePath + File.separator + fileName
        thread {
            try {
                syncDownload(url, path)
            }catch (e:Exception){
                e.printStackTrace()
                val err = if(e.localizedMessage == null){"updater run download error!"}else{e.localizedMessage}
                this.listener?.run { onDownloadFinish(null,err) }
            }finally {
                this.isLoading = false
            }

        }
    }

    private fun syncDownload(url: String, path: String) {
        isLoading = true
        val uri = URL(url)
        val connection = uri.openConnection() as HttpURLConnection
        connection.connect()
        if (connection.responseCode != HTTP_OK) {
            val error =
                "FileDownload Server returned HTTP ${connection.responseCode} ${connection.responseMessage}"
            listener?.run {
                onDownloadFinish(null, error)
            }
            return
        }
        val fileLength = connection.contentLength
        val file = File(path)
        if (!file.exists()) {
            if (!file.parentFile!!.exists()) {
                file.parentFile!!.mkdirs()
            }
        } else {
            println("FileDownload file exist , path : ${file.absolutePath}")
        }

        connection.inputStream.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                val data = ByteArray(1024)
                var total: Long = 0
                var count: Int
                while (inputStream.read(data).also { count = it } != -1) {
                    if (cancel) {
                        listener?.run { onDownloadFinish(null, "取消任务下载") }
                        return
                    }
                    total += count.toLong()
                    if (fileLength > 0) { // only if total length is known
                        listener?.run { onDownloadProgress((total * 100 / fileLength).toInt()) }
                    }
                    outputStream.write(data, 0, count)
                }
            }
        }
        isLoading = true
        listener?.run { onDownloadFinish(file, "") }

    }


    fun installApp(context : Context, apkPath: String) {

        val apkFile = File(apkPath)
        val intent = Intent(Intent.ACTION_VIEW)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK //安装好了，点打开，打开新版本应用的。
        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            val apkUri: Uri =
                FileProvider.getUriForFile(context, context.packageName+VersionUpdater.authorities, apkFile)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
        }
        context.startActivity(intent)
    }

    fun versionName(context: Context):String{
        return  packageInfo(context).versionName
    }

    fun versionCode(context: Context):Long{
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            packageInfo(context).longVersionCode
        } else {
            packageInfo(context).versionCode.toLong()
        }
    }

    private fun packageInfo(context: Context): PackageInfo {
        val packageManager = context.packageManager
        return packageManager.getPackageInfo(context.packageName, 0)
    }


    interface Listener {
        fun onDownloadFinish(file: File?, error: String)
        fun onDownloadProgress(progress: Int)
    }

}