package cn.ymex.kitx.anhttp.lifecycle

data class PageState(var status: ViewStatus = ViewStatus.NORMAL, var throwable: Throwable? = null) {
    override fun toString(): String {
        return "PageState(status=$status, message='$throwable')"
    }
}
