package ua.ilyadreamix.amino.data.dto.security

import kotlinx.serialization.*

@Serializable
data class SendPublicKeyRequest(
    @SerialName("key_chain")
    val keyChain: List<String>,
    @SerialName("uid")
    val uid: String,
    @SerialName("timestamp")
    val timestamp: Long = System.currentTimeMillis()
)