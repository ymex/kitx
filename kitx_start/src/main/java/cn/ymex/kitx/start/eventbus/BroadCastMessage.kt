package cn.ymex.kitx.start.eventbus


class BroadCastMessage(private val message: String, val any: Any = Any()) {
    fun eq(broadCast: BroadCastMessage): Boolean {
        return broadCast.message == message
    }

    override fun toString(): String {
        return "[BroadCastMessage]:{message = ${message}, any:${any}}"
    }
}