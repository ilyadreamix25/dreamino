package ua.ilyadreamix.amino.data.dto.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray

@Serializable
data class Community(
    @SerialName("icon")
    val icon: String,
    @SerialName("ndcId")
    val ndcId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("membersCount")
    val membersCount: Int,
    @SerialName("promotionalMediaList")
    val mediaList: JsonArray?
)