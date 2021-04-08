package cn.ymex.kitx.tips.time

import android.os.CountDownTimer

/**
 * 倒计时
 *TickTime(1000, object : TickTime.OnTickTimeListener {
 *override fun onTickStop(tag: Int) {}
 *override fun onTick(tag: Int, time: String) {}
 *}).setFormat(TickTime.computeMillis { millis, second, minute, hour, day ->
 *"倒计时"
 *}).start(60*1000)
 */


class TickTime(
    val interval: Long = 1000,
    var tickTimeListener: OnTickTimeListener,
    val tag: Int = 0
) {
    companion object {
        /**
         * 数字填充 0
         *
         * @param num
         * @return
         */
        fun fillZero(num: Long): String {

            return if (num < 10) {
                "0$num"
            } else num.toString()
        }

        fun computeMillis(format: (millis: Long, second: Long, minute: Long, hour: Long, day: Long) -> String): Format {
            return object : Format {
                override fun formtTime(millis: Long): String {
                    val _second = millis / 1000
                    val mil = (millis % 1000)
                    val second = (_second % 60)
                    val minute = (_second % 3600 / 60)
                    val hour = (_second / 3600 % 24)
                    val day = (_second / 3600 / 24)
                    return format(mil, second, minute, hour, day)
                }
            }
        }
    }


    interface Format {
        fun formtTime(millis: Long): String
    }

    interface OnTickTimeListener {
        fun onTickStop(tag: Int)
        fun onTick(tag: Int, time: String)
    }

    private var mTimer: CountDownTimer? = null
    private var countDownIng = false

    private var format: Format = object :
        Format {
        override fun formtTime(millis: Long): String {
            return millis.toString()
        }

    }


    fun setFormat(format: Format): TickTime {
        this.format = format
        return this
    }

    @Synchronized
    fun start(millis: Long): TickTime {
        cancel()
        mTimer = object : CountDownTimer(millis, interval) {
            override fun onFinish() {
                countDownIng = false
                tickTimeListener.onTickStop(tag)
            }

            override fun onTick(millisUntilFinished: Long) {
                tickTimeListener.onTick(tag, format.formtTime(millisUntilFinished))
            }
        }
        countDownIng = true
        mTimer?.start()
        return this
    }


    fun finish() {
        cancel()
        mTimer?.onFinish()
    }

    //停止计时
    fun cancel() {
        countDownIng = false
        mTimer?.cancel()
    }

}
