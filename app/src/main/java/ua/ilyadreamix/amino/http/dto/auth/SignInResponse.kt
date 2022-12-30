package ua.ilyadreamix.amino.http.dto.auth

import com.google.gson.annotations.SerializedName
import ua.ilyadreamix.amino.http.dto.objects.Account
import ua.ilyadreamix.amino.http.dto.objects.UserProfile
import ua.ilyadreamix.amino.http.dto.core.BaseResponse

data class SignInResponse(
    @SerializedName("auid")
    val auid: String,
    @SerializedName("account")
    val account: Account,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("userProfile")
    val userProfile: UserProfile,
    @SerializedName("sid")
    val sid: String
) : BaseResponse()