package ua.ilyadreamix.amino.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginByEmailRequest(
    @SerialName("email")
    val email: String,
    @SerialName("secret")
    val secret: String,
    @SerialName("clientType")
    val clientType: Int = 100,
    @SerialName("deviceID")
    val deviceId: String,
    @SerialName("v")
    val v: Int = 2,
    @SerialName("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)
