package ua.ilyadreamix.amino.data.dto.posts

import kotlinx.serialization.*
import ua.ilyadreamix.amino.data.dto.core.BaseResponse
import ua.ilyadreamix.amino.data.dto.objects.Featured

@Serializable
data class FeedFeaturedPostsResponse(
    @SerialName("featuredList")
    val featuredList: List<Featured>
) : BaseResponse()