package ua.ilyadreamix.amino.data.dto.auth

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import ua.ilyadreamix.amino.data.dto.core.BaseResponse
import ua.ilyadreamix.amino.data.dto.objects.Account
import ua.ilyadreamix.amino.data.dto.objects.Profile

@Serializable
data class LoginByEmailResponse(
    @SerialName("auid")
    val auid: String,
    @SerialName("account")
    val account: Account,
    @SerialName("secret")
    val secret: String,
    @SerialName("userProfile")
    val userProfile: Profile,
    @SerialName("sid")
    val sid: String
) : BaseResponse()