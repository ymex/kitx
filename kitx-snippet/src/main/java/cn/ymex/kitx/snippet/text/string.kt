package cn.ymex.kitx.snippet.text

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * 字串转Float
 */
fun String.toSafeFloat(defValue: Float = 0F): Float {
    var value = defValue
    try {
        value = toFloat()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return value
}

/**
 * 字串转Int
 */
fun String.toSafeInt(defValue: Int = 0): Int {
    var value = defValue
    try {
        value = toInt()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return value
}

/**
 * 字串转Double
 */
fun String.toSafeDouble(defValue: Double = 0.0): Double {
    var value = defValue
    try {
        value = toDouble()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return value
}

/**
 * 格式化小数后N位
 */
fun Double.format(scale: Int = 2): String {
    var bd = BigDecimal(this)
    bd = bd.setScale(scale, RoundingMode.HALF_UP)
    return bd.toString()
}

/**
 * 格式化小数后N位
 */
fun Float.format(scale: Int = 2): String {
    var bd = BigDecimal(this.toDouble())
    bd = bd.setScale(scale, RoundingMode.HALF_UP)
    return bd.toString()
}

/**
 * 格式化小数后N位
 */
fun String.formatNumber(scale: Int = 2): String {
    return format("%.${scale}f", this)
}


fun <T> Boolean.judge(t: T, f: T): T {
    return if (this) t else f
}

/**
 * 除正常的
 * 大于0时为true，小于等于0 时为 false
 */
fun String.toSafeBoolean(defValue: Boolean): Boolean {
    var value = defValue
    try {
        value = this.toBoolean()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return value
}


fun Int.toBoolean(): Boolean {
    return (this != 0).judge(t = true, f = false)
}