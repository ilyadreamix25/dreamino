package ua.ilyadreamix.amino.data.dto.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Author(
    @SerialName("accountMembershipStatus")
    val accountMembershipStatus: Int,
    @SerialName("avatarFrame")
    val avatarFrame: AvatarFrame?,
    @SerialName("avatarFrameId")
    val avatarFrameId: String?,
    @SerialName("followingStatus")
    val followingStatus: Int,
    @SerialName("icon")
    val icon: String?,
    @SerialName("isGlobal")
    val isGlobal: Boolean,
    @SerialName("isNicknameVerified")
    val isNicknameVerified: Boolean,
    @SerialName("level")
    val level: Int,
    @SerialName("membersCount")
    val membersCount: Int,
    @SerialName("membershipStatus")
    val membershipStatus: Int,
    @SerialName("ndcId")
    val ndcId: Int,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("reputation")
    val reputation: Int,
    @SerialName("role")
    val role: Int,
    @SerialName("status")
    val status: Int,
    @SerialName("uid")
    val uid: String
) {
    @Serializable
    data class AvatarFrame(
        @SerialName("frameId")
        val frameId: String,
        @SerialName("frameType")
        val frameType: Int,
        @SerialName("icon")
        val icon: String,
        @SerialName("name")
        val name: String,
        @SerialName("resourceUrl")
        val resourceUrl: String,
        @SerialName("status")
        val status: Int,
        @SerialName("version")
        val version: Int
    )
}