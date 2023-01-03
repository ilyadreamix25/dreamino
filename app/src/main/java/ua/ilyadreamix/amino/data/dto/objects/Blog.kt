package ua.ilyadreamix.amino.data.dto.objects

import kotlinx.serialization.*
import kotlinx.serialization.json.JsonArray

@Serializable
data class Blog(
    @SerialName("blogId")
    val blogId: String,
    @SerialName("content")
    val content: String?,
    @SerialName("ndcId")
    val ndcId: Int,
    @SerialName("author")
    val author: Author,
    @SerialName("title")
    val title: String,
    @SerialName("votedValue")
    val votedValue: Int,
    @SerialName("votesCount")
    val votesCount: Int,
    @SerialName("commentsCount")
    val commentsCount: Int,
    @SerialName("createdTime")
    val createdTime: String,
    @SerialName("mediaList")
    val mediaList: List<JsonArray>?
)