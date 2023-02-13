package cn.ymex.kitx.anhttp

/**
 * 请求参数
 */
class Param(common: Boolean) : LinkedHashMap<String, Any>() {

    companion object {
        fun stream(addCommonParams: Boolean = true): Param {
            return Param(addCommonParams)
        }
    }

    init {
        if (common) {
            AnHttpManager.instance.commonParams.forEach { (k, v) ->
                put(k, v)
            }
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
        return "Param(${builder})"
    }


}