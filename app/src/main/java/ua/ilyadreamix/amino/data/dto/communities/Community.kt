package ua.ilyadreamix.amino.data.dto.communities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Community(
    @SerialName("icon")
    val icon: String,
    @SerialName("ndcId")
    val ndcId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("membersCount")
    val membersCount: Int
)