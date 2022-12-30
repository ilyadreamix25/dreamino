package ua.ilyadreamix.amino.http.dto.auth

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("clientType")
    val clientType: Int = 100,
    @SerializedName("deviceID")
    val deviceId: String,
    @SerializedName("v")
    val v: Int = 2,
    @SerializedName("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)