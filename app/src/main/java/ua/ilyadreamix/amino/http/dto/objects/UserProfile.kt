package ua.ilyadreamix.amino.http.dto.objects

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("role")
    val role: Int,
    @SerializedName("content")
    val content: String?,
    @SerializedName("createdTime")
    val createdTime: String
)