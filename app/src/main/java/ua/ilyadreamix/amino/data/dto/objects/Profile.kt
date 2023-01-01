package ua.ilyadreamix.amino.data.dto.objects

import kotlinx.serialization.*

@Serializable
data class Profile(
    @SerialName("uid")
    val uid: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("icon")
    val icon: String?,
    @SerialName("role")
    val role: Int,
    @SerialName("content")
    val content: String?,
    @SerialName("createdTime")
    val createdTime: String
)