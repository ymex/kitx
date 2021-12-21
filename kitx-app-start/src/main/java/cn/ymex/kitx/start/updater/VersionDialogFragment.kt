package cn.ymex.kitx.start.updater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import cn.ymex.kitx.start.R
import cn.ymex.kitx.snippet.view.find
import cn.ymex.kitx.snippet.view.visibilityGone
import cn.ymex.kitx.snippet.view.visibilityShow
import java.io.File

/**
 * 版本更新弹框
 */
class VersionDialogFragment : DialogFragment(), VersionUpdater.Listener {

    private lateinit var tvTitle: TextView
    private lateinit var tvContent: TextView
    private lateinit var btCancel: TextView
    private lateinit var btOK: TextView
    private lateinit var vLine: View
    private lateinit var vOptWrap: View
    private lateinit var vProgressWrap: View
    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgressTip: TextView
    private lateinit var tvErrorTip: TextView

    var updateConfig: UpdateConfig? = null
    var versionConfig: VersionConfig? = null


    companion object{
        /**
         * 版本信息的弹框
         */
        fun show(@NonNull  manager: FragmentManager, @NonNull versionConfig: VersionConfig = VersionConfig(), tag:String = "updater_version_fragment"){
            val fragment = VersionDialogFragment()
            fragment.setStyle(STYLE_NO_TITLE,0)
            fragment.versionConfig = versionConfig
            fragment.show(manager,tag)
        }
        /**
         * 下载更新弹框
         */
        fun show(@NonNull  manager: FragmentManager, @NonNull updateConfig: UpdateConfig,tag:String = "updater_update_fragment"){
            val fragment = VersionDialogFragment()
            fragment.setStyle(STYLE_NO_TITLE,0)
            fragment.updateConfig = updateConfig
            fragment.show(manager,tag)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_default_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        if (updateConfig == null && versionConfig == null) {
            Toast.makeText(requireContext(), "update version has no config!", Toast.LENGTH_LONG)
                .show()
            dismiss()
            return
        }
        if (versionConfig == null) {
            initUpdateView()
        } else {
            initVersionView()
        }

    }

    private fun initUpdateView() {
        updateConfig?.let {
            btCancel.visibilityShow()
            vLine.visibilityShow()
            if (it.forceUpdate) {
                isCancelable = false
                btCancel.visibilityGone()
                vLine.visibilityGone()
            }
            //更新
            vOptWrap.visibilityShow()
            vProgressWrap.visibilityGone()

            btOK.visibilityShow()
            btOK.text = "更新"
            btOK.setOnClickListener { _ ->
                isCancelable = false
                vOptWrap.visibilityGone()
                vProgressWrap.visibilityShow()
                if (VersionUpdater.instance.isLoading()) {
                    return@setOnClickListener
                }
                tvErrorTip.visibilityGone()
                VersionUpdater.instance.download(requireContext(), it.url, it.apkName, this)
            }
            btCancel.setOnClickListener {
                dismiss()
            }

            tvTitle.text = it.title
            tvContent.text = when {
                it.isHtml -> {
                    HtmlCompat.fromHtml(it.content, FROM_HTML_MODE_COMPACT)
                }
                else -> {
                    it.content
                }
            }


        }
    }

    private fun initVersionView() {
        versionConfig?.let {
            vOptWrap.visibilityShow()
            vProgressWrap.visibilityGone()
            btCancel.visibilityGone()
            btOK.visibilityShow()
            btOK.text = "确定"
            vLine.visibilityGone()
            btOK.setOnClickListener { dismiss() }
            tvTitle.text = it.title

            tvContent.text = when {
                it.content.isEmpty() -> {
                    val name = VersionUpdater.instance.versionName(requireContext())
                    val code = VersionUpdater.instance.versionCode(requireContext())
                    "版本名称：${name} \n版本编号：${code}"
                }
                it.isHtml -> {
                    HtmlCompat.fromHtml(it.content, FROM_HTML_MODE_COMPACT)
                }
                else -> {
                    it.content
                }
            }

        }
    }


    private fun initView(view: View) {
        tvTitle = view.find(R.id.tvTitle)
        tvContent = view.find(R.id.tvContent)
        btCancel = view.find(R.id.btnCancel)
        btOK = view.find(R.id.btnOK)
        vLine = view.find(R.id.vLine)
        vOptWrap = view.find(R.id.vOptWrap)
        vProgressWrap = view.find(R.id.vProgressWrap)
        progressBar = view.find(R.id.progressDownload)
        tvProgressTip = view.find(R.id.tvProgressTip)
        tvErrorTip = view.find(R.id.tvErrorTip)
    }


    override fun onDownloadFinish(file: File?, error: String) {
        if (file != null && error.isEmpty()) {
            //file
            updateConfig?.let {
                requireActivity().runOnUiThread {
                    btOK.text = "安装"
                    vProgressWrap.visibilityGone()
                    vOptWrap.visibilityShow()
                    btOK.setOnClickListener {
                        VersionUpdater.instance.installApp(requireContext(), file.absolutePath)
                    }
                    if (it.autoInstall) {
                        VersionUpdater.instance.installApp(requireContext(), file.absolutePath)
                    }
                }
            }
        } else {
            requireActivity().runOnUiThread {
                initUpdateView()
                tvErrorTip.visibilityShow()
                tvErrorTip.text = "下载出错，新重新尝试更新！"
            }
        }
    }

    override fun onDownloadProgress(progress: Int) {
        requireActivity().runOnUiThread {
            tvProgressTip.text = "${progress}%"
            progressBar.progress = progress
        }
    }
}