package ua.ilyadreamix.amino.http.dto.auth

import com.google.gson.annotations.SerializedName

data class SendPublicCertificatesRequest(
    @SerializedName("key_chain")
    val certificates: List<String>,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)

