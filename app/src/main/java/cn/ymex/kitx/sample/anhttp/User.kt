package cn.ymex.kitx.sample.anhttp
import com.google.gson.annotations.SerializedName


/**
 * Created by ymex on 2020/2/5.
 * About:
 */

data class UserInfo(
    @SerializedName("userInfo")
    val userInfo: User = User()
)



data class User(
    @SerializedName("age")
    val age: Int = 0, // 0
    @SerializedName("clinic_id")
    val clinicId: Int = 0, // 2
    @SerializedName("clinic_name")
    val clinicName: String = "", // 包河佳德
    @SerializedName("createTime")
    val createTime: String = "", // Sep 21, 2019 9:50:45 AM
    @SerializedName("deptId")
    val deptId: String = "", // 0
    @SerializedName("go_time")
    val goTime: Int = 0, // 0
    @SerializedName("id")
    val id: Int = 0, // 307
    @SerializedName("is_delete")
    val isDelete: Int = 0, // 0
    @SerializedName("is_go")
    val isGo: Int = 0, // 1
    @SerializedName("is_sys")
    val isSys: Int = 0, // 0
    @SerializedName("jobNo")
    var jobNo: String = "",
    @SerializedName("password")
    val password: String = "", // 3a438b765ab93933b3e8ea37e0907608
    @SerializedName("realname")
    val realname: String = "", // 李钧
    @SerializedName("ring_id")
    val ringId: Int = 0, // 7
    @SerializedName("ring_user_id")
    val ringUserId: Int = 0, // 0
    @SerializedName("sex")
    val sex: Int = 0, // 0
    @SerializedName("templateId")
    val templateId: Int = 0, // 0
    @SerializedName("token")
    val token: String = "", // 15abbd5beee6fc7e42db655ed8c2253b
    @SerializedName("userDesc")
    val userDesc: String = "",
    @SerializedName("userImg")
    val userImg: String = "", // sysuser/201912/0c12da43cab1484e8d2f86f6952252fa.png
    @SerializedName("userName")
    val userName: String = "", // 18963793507
    @SerializedName("user_origin")
    val userOrigin: Int = 0, // 0
    @SerializedName("userState")
    val userState: Int = 0, // 1
    @SerializedName("userType")
    val userType: Int = 0 // 0
)