package ua.ilyadreamix.amino.data.dto.communities

import ua.ilyadreamix.amino.data.dto.core.BaseResponse
import ua.ilyadreamix.amino.data.dto.objects.Community
import kotlinx.serialization.*

@Serializable
data class CommunityInfoResponse(
    @SerialName("community")
    val community: Community
) : BaseResponse()