package cn.ymex.kitx.start.eventbus


class EventMessage(private val message: String, val any: Any = Any()) {
    fun eq(event: EventMessage): Boolean {
        return event.message == message
    }

    override fun toString(): String {
        return "[EventMessage]:{message = ${message}, any:${any}}"
    }
}