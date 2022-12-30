package ua.ilyadreamix.amino.http.dto.core

import com.google.gson.annotations.SerializedName

open class BaseResponse(
    /** 270 - verify account */
    @SerializedName("api:statuscode")
    val statusCode: Int = -1,
    @SerializedName("api:message")
    val message: String = "Unable to make an request",
    @SerializedName("url")
    val url: String? = null
)
