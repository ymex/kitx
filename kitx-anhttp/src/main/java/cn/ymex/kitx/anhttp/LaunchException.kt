package cn.ymex.kitx.anhttp


import android.os.Bundle

class LaunchException(val code: Int = 0, message:String = "",val bundle: Bundle = Bundle.EMPTY) :Exception(message)