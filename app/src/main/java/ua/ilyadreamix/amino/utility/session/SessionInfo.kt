package ua.ilyadreamix.amino.utility.session

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionInfo(
    @SerialName("lastLogin")
    val lastLogin: Long = 0,
    @SerialName("secret")
    val secret: String,
    @SerialName("sessionId")
    val sessionId: String,
    @SerialName("deviceId")
    val deviceId: String,
    @SerialName("userId")
    val userId: String
)