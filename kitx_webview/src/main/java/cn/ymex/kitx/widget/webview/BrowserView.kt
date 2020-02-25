package cn.ymex.kitx.widget.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.os.Process
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import cn.ymex.kitx.widget.webview.bridge.BridgeWebView
import com.tencent.smtt.sdk.CookieManager
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.WebSettings

class BrowserView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val mWebView: WebView = WebView(getContext())
    private var mProgress:Progressable? =
        DefalutProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal)

    private var titleView:TextView? = null

    init {
        addView(mWebView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        if (mProgress is View) {
            addView(mProgress as View, LayoutParams(LayoutParams.MATCH_PARENT, dip2px(2.4f)))
        }
        initSetting(mProgress)
    }

    private fun dip2px(dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    private fun initSetting(progressable: Progressable?) {
        getWebView().webViewClient = BrowserClient(progressable)
        getWebView().webChromeClient = BrowserChromeClient(progressable,titleView)
    }

    /**
     * inside webview
     */
    fun getWebView(): WebView {
        return mWebView
    }

    /**
     * webview setting
     */
    fun getSetting(): WebSettings {
        return mWebView.settings
    }

    /**
     * webview progress
     */
    fun getProgress():Progressable? {
        return  mProgress
    }


    /**
     * set progress  for browser
     */
    fun setProgress(progress: Progressable?, params: LayoutParams? = null) {
        if (progress is View) {
            if (mProgress is View) {
                removeView(mProgress as View)
            }
            if (params != null) {
                addView(progress, params)
            } else {
                addView(progress)
            }
        }

        mProgress = progress
    }

    /**
     * load html or file or url
     */
    fun loadData(data: String) {
        data.run {
            when {
                this.startsWith("http://") or this.startsWith("https://") -> {
                    getWebView().loadUrl(this)
                }
                this.startsWith("file://") -> {
                    getWebView().loadUrl(this)
                }
                else -> {
                    getWebView().loadDataWithBaseURL(null, this, "text/html", "utf-8", null)
                }
            }
        }
    }


    /**
     * load url
     */
    fun loadUrl(url: String) {
        getWebView().loadUrl(url)
    }





    interface Progressable {
        fun onProgressChanged(progress: Int)
        fun onStart()
        fun onFinish()
    }


    class WebView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
    ) : BridgeWebView(context, attrs) {

        init {
            setBackgroundColor(85621)
            initSetting()
        }

        @SuppressLint("SetJavaScriptEnabled")
        private fun initSetting() {
            val webSetting = this.settings
            webSetting.javaScriptEnabled = true
            webSetting.javaScriptCanOpenWindowsAutomatically = true
            webSetting.allowFileAccess = true
            webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            webSetting.setSupportZoom(true)
            webSetting.builtInZoomControls = true
            webSetting.useWideViewPort = true
            webSetting.setSupportMultipleWindows(true)
//        webSetting.loadWithOverviewMode = true
            webSetting.setAppCacheEnabled(true)
//        webSetting.databaseEnabled = true


            webSetting.setGeolocationEnabled(true)
            webSetting.setAppCacheMaxSize(Long.MAX_VALUE)

            webSetting.pluginState = WebSettings.PluginState.ON
            webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH)

            webSetting.cacheMode = WebSettings.LOAD_NO_CACHE
            // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension

            // Enable remote debugging via chrome://inspect
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setWebContentsDebuggingEnabled(true)
            }

            // Allow use of Local Storage
            webSetting.domStorageEnabled = true

            // AppRTC requires third party cookies to work
            val cookieManager = CookieManager.getInstance()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.setAcceptThirdPartyCookies(this, true)
            }

            //允许 http ,https 混合模式请求图片，manifest.xml 添加：android:usesCleartextTraffic="true"
            settings.blockNetworkImage = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            //播放视频时，可自动播放
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                settings.mediaPlaybackRequiresUserGesture = false
            }
            // WebView UA
            webSetting.userAgentString = webSetting.userAgentString + " app/kitx(webivew) "

        }

        override fun drawChild(
            canvas: Canvas,
            child: View,
            drawingTime: Long
        ): Boolean {
            val ret = super.drawChild(canvas, child, drawingTime)
            canvas.save()
            val paint = Paint()
            paint.color = 0x7fff0000
            paint.textSize = 24f
            paint.isAntiAlias = true
            if (x5WebViewExtension != null) {
                canvas.drawText(
                    this.context.packageName + "-pid:"
                            + Process.myPid(), 10f, 50f, paint
                )
                canvas.drawText(
                    "X5  Core:" + QbSdk.getTbsVersion(this.context), 10f, 100f, paint
                )
            } else {
                canvas.drawText(
                    this.context.packageName + "-pid:"
                            + Process.myPid(), 10f, 50f, paint
                )
                canvas.drawText("Sys Core", 10f, 100f, paint)
            }
            canvas.drawText(Build.MANUFACTURER, 10f, 150f, paint)
            canvas.drawText(Build.MODEL, 10f, 200f, paint)
            canvas.restore()
            return ret
        }
    }

    /**
     * 进度条
     */
    class DefalutProgressBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
    ) : ProgressBar(context, attrs, defStyleAttr), Progressable {

        override fun onProgressChanged(progress: Int) {
            setProgress(progress)
        }

        override fun onStart() {
            visibility = View.VISIBLE
        }

        override fun onFinish() {
            visibility = View.GONE
        }

    }
}