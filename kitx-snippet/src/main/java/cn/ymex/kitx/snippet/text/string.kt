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
fun Double.format(scale: Int = 2,mode:RoundingMode =  RoundingMode.HALF_UP): String {
    var bd = BigDecimal(this)
    bd = bd.setScale(scale,mode)
    return bd.toString()
}

/**
 * 格式化小数后N位
 */
fun Float.format(scale: Int = 2,mode:RoundingMode =  RoundingMode.HALF_UP): String {
    var bd = BigDecimal(this.toDouble())
    bd = bd.setScale(scale, mode)
    return bd.toString()
}

/**
 * 格式化小数后N位
 */
fun String.format(scale: Int = 2,mode:RoundingMode =  RoundingMode.HALF_UP): String {
    val dn = toSafeDouble()
    return dn.format(scale,mode)
}


fun <T> Boolean.judge(t: T, f: T): T {
    return if (this) t else f
}



fun Number.toBoolean(): Boolean {
    return (this != 0).judge(t = true, f = false)
}

