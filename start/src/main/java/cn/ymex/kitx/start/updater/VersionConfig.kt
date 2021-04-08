package cn.ymex.kitx.start.updater

/***
 * @param url
 * @param apkName 下载的文件名
 * @param content
 * @param goWeb
 * @param autoInstall
 * @param forceUpdate
 */
data class UpdateConfig(val url:String ,
                        val apkName:String,
                        val content:String,
                        val title:String = "发现新版本",
                        val goWeb:Boolean = false,
                        val autoInstall:Boolean = true,
                        val forceUpdate:Boolean = false,
                        val isHtml:Boolean = false)
/***
 * @param title
 * @param content
 */
data class VersionConfig( val title:String = "当前为最新版本",
                          val content:String = "",
                          val isHtml:Boolean = false)