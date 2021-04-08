package cn.ymex.kitx.tips.device

import android.os.Build

/**
 * 品牌
 */
fun brand():String{
    return Build.BRAND
}

/**
 * 型号
 */
fun model():String{
    return Build.MODEL
}

/**
 * 制造商
 */
fun manufacturer():String{
    return Build.MANUFACTURER
}

/**
 * 系统版本
 */
fun osVersionName():String{
    return Build.VERSION.RELEASE
}
/**
 * 系统版本号
 */
fun osVersionCode():Int{
    return Build.VERSION.SDK_INT
}


/**
 * 设备唯一标识
 * https://www.jianshu.com/p/1c7ef27d6db4
 * 通过阿里云的utdid 库获取的标识重新安装应用时可能改变。
 * https://help.aliyun.com/document_detail/159082.html
 */
@Deprecated("返回为空不要使用", ReplaceWith(""))
fun UDID():String{
    return ""
}

/**
 * 开放匿名标识
 * 建议：
 * https://github.com/gzu-liyujiang/Android_CN_OAID
 * https://mtj.baidu.com/static/userguide/book/android/oaid.html
 */
@Deprecated("返回为空不要使用", ReplaceWith(""))
fun OAID():String{
    return ""
}