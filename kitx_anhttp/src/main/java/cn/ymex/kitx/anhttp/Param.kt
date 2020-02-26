package cn.ymex.kitx.anhttp

/**
 * 请求参数
 */
class Param : LinkedHashMap<String, Any>() {

    companion object{
        fun stream() :Param{
            return Param()
        }
    }

    init {
        AnHttpManager.instance.commonParams.forEach { (k, v) ->
            put(k, v)
        }
    }


    fun with(key: String, value: Any): Param {
        put(key, value)
        return this
    }

    override fun toString(): String {
        val builder = StringBuilder("{")
        forEach() { (k, v) ->
            builder.append(k)
            builder.append(" : ")
            builder.append(v.toString())
            builder.append(", ")
        }
        builder.append("}")
        return "Param()"
    }


}