package ua.ilyadreamix.amino.data.dto.objects

import kotlinx.serialization.*
import kotlinx.serialization.json.JsonArray

@Serializable
data class Featured(
    @SerialName("createdTime")
    val createdTime: String,
    @SerialName("expiredTime")
    val expiredTime: String,
    @SerialName("featuredType")
    val featuredType: Int,
    @SerialName("refObject")
    val refObject: RefObject,
    @SerialName("refObjectId")
    val refObjectId: String?,
    @SerialName("refObjectType")
    val refObjectType: Int
) {
    @Serializable
    data class RefObject(
        @SerialName("author")
        val author: Author,
        @SerialName("blogId")
        val blogId: String?,
        @SerialName("commentsCount")
        val commentsCount: Int,
        @SerialName("content")
        val content: String?,
        @SerialName("createdTime")
        val createdTime: String,
        @SerialName("itemId")
        val itemId: String?,
        @SerialName("label")
        val label: String?,
        @SerialName("mediaList")
        val mediaList: JsonArray?,
        @SerialName("ndcId")
        val ndcId: Int,
        @SerialName("status")
        val status: Int,
        @SerialName("title")
        val title: String?,
        @SerialName("votedValue")
        val votedValue: Int,
        @SerialName("votesCount")
        val votesCount: Int
    )
}