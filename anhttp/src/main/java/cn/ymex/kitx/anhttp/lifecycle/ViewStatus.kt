package cn.ymex.kitx.anhttp.lifecycle

/**
 * 视图状态
 */
enum class ViewStatus {
    EMPTY, //空数据状态
    NORMAL, //正常状态
    LOADING, //加载中
    ERR //一般错误
}