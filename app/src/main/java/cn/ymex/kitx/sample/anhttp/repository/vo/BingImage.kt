package cn.ymex.kitx.sample.anhttp.repository.vo
import com.google.gson.annotations.SerializedName


data class BingImageResult(
    @SerializedName("images")
    val images: List<Image> = listOf(),
    @SerializedName("tooltips")
    val tooltips: Tooltips = Tooltips()
)

data class Image(
    @SerializedName("bot")
    val bot: Int = 0, // 1
    @SerializedName("copyright")
    val copyright: String = "", // 蒙多河源头的瀑布，西班牙阿尔瓦赛特里奥帕尔 (© Westend61/Getty Images)
    @SerializedName("copyrightlink")
    val copyrightlink: String = "", // https://www.bing.com/search?q=%E8%92%99%E5%A4%9A%E6%B2%B3&form=hpcapt&mkt=zh-cn
    @SerializedName("drk")
    val drk: Int = 0, // 1
    @SerializedName("enddate")
    val enddate: String = "", // 20200225
    @SerializedName("fullstartdate")
    val fullstartdate: String = "", // 202002241600
    @SerializedName("hs")
    val hs: List<Any> = listOf(),
    @SerializedName("hsh")
    val hsh: String = "", // 06c2330a2c3285af58063154f33b2016
    @SerializedName("quiz")
    val quiz: String = "", // /search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20200224_MundoFalls%22&FORM=HPQUIZ
    @SerializedName("startdate")
    val startdate: String = "", // 20200224
    @SerializedName("title")
    val title: String = "",
    @SerializedName("top")
    val top: Int = 0, // 1
    @SerializedName("url")
    val url: String = "", // /th?id=OHR.MundoFalls_ZH-CN5545236650_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp
    @SerializedName("urlbase")
    val urlbase: String = "", // /th?id=OHR.MundoFalls_ZH-CN5545236650
    @SerializedName("wp")
    val wp: Boolean = false // true
)

data class Tooltips(
    @SerializedName("loading")
    val loading: String = "", // 正在加载...
    @SerializedName("next")
    val next: String = "", // 下一个图像
    @SerializedName("previous")
    val previous: String = "", // 上一个图像
    @SerializedName("walle")
    val walle: String = "", // 此图片不能下载用作壁纸。
    @SerializedName("walls")
    val walls: String = "" // 下载今日美图。仅限用作桌面壁纸。
)