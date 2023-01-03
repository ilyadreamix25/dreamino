package ua.ilyadreamix.amino.data.dto.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseResponse(
    @SerialName("api:statuscode")
    val statusCode: Int = -1,
    @SerialName("api:message")
    val message: String = "Unable to make an request",
    @SerialName("url")
    val url: String? = null,
    @SerialName("api:timestamp")
    val timestamp: String = "0",
)
