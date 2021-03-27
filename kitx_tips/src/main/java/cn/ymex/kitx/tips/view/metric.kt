package cn.ymex.kitx.tips.view

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

val metrics: DisplayMetrics =
    Resources.getSystem().displayMetrics


/**
 * 屏幕高度
 *
 * @return px
 */
fun getScreenHeight(): Int {
    return metrics.heightPixels
}

/**
 * 屏幕宽度
 *
 * @return px
 */
fun getScreenWidth(): Int {
    return metrics.widthPixels
}


val Float.px:Int
    get() =TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,this,Resources.getSystem().displayMetrics).toInt()

val Int.px:Float
    get() =TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,this.toFloat(),Resources.getSystem().displayMetrics)


val Int.dp:Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),Resources.getSystem().displayMetrics)

val Float.dp:Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this,Resources.getSystem().displayMetrics)


val Int.sp:Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,this.toFloat(),Resources.getSystem().displayMetrics)

val Float.sp:Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,this,Resources.getSystem().displayMetrics)


/**
 * 状态栏高度
 *
 * @return px
 */
fun statusBarHeight(): Int {

    val resourceId =
        Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId != 0) {
        Resources.getSystem().getDimensionPixelSize(resourceId)
    } else {
        0
    }
}

/**
 * 底部导航栏高度
 * @return px
 */
fun navBarHeight(): Int {
    val resourceId =
        Resources.getSystem().getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId != 0) {
        Resources.getSystem().getDimensionPixelSize(resourceId)
    } else {
        0
    }
}