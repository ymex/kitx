package cn.ymex.kitx.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * Created by ymex on 2020/2/13.
 * About:
 */
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

/**
 * dp to px
 *
 * @param dp dip
 * @return int
 */
fun Float.topx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this,
        Resources.getSystem().displayMetrics
    ).toInt()
}


/**
 * px to dip
 *
 * @param px px
 * @return float
 */
fun Int.todip(): Float {
    return this / metrics.density + 0.5f
}


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