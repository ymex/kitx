package cn.ymex.kitx.snippet.view

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

// px to dp
val Float.px:Float
    get() {
        val density = Resources.getSystem().displayMetrics.density
        return (this/density +0.5).toFloat()
    }

val Int.px:Float
    get() {
        val density = Resources.getSystem().displayMetrics.density
        return (this/density +0.5).toFloat()
    }


//val Float.px:Int
//    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,this,Resources.getSystem().displayMetrics).toInt()
//
//val Int.px:Int
//    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,this.toFloat(),Resources.getSystem().displayMetrics).toInt()

// dp to px
val Int.dp:Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),Resources.getSystem().displayMetrics).toInt()

val Float.dp:Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,this,Resources.getSystem().displayMetrics).toInt()


// sp to px
val Int.sp:Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,this.toFloat(),Resources.getSystem().displayMetrics).toInt()

val Float.sp:Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,this,Resources.getSystem().displayMetrics).toInt()


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