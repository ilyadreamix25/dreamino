package ua.ilyadreamix.amino.http.dto.objects

import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("uid")
    val uid: String?,
    @SerializedName("aminoId")
    val aminoId: String?,
    @SerializedName("email")
    val email: String?,
)