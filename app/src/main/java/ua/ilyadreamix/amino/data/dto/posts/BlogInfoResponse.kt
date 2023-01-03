package ua.ilyadreamix.amino.data.dto.posts

import kotlinx.serialization.*
import ua.ilyadreamix.amino.data.dto.core.BaseResponse
import ua.ilyadreamix.amino.data.dto.objects.Blog

@Serializable
data class BlogInfoResponse(
    @SerialName("blog")
    val blog: Blog
) : BaseResponse()
