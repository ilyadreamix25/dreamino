package ua.ilyadreamix.amino.http.dto.objects

import com.google.gson.annotations.SerializedName

data class Community(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("ndcId")
    val ndcId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("membersCount")
    val membersCount: Int
) {

}
