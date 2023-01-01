package ua.ilyadreamix.amino.data.dto.objects

import kotlinx.serialization.*
import ua.ilyadreamix.amino.data.dto.core.BaseResponse

@Serializable
data class Account(
    @SerialName("uid")
    val uid: String?,
    @SerialName("aminoId")
    val aminoId: String?,
    @SerialName("email")
    val email: String?,
) : BaseResponse()