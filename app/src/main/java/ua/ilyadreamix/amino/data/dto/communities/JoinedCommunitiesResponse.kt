package ua.ilyadreamix.amino.data.dto.communities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ua.ilyadreamix.amino.data.dto.core.BaseResponse
import ua.ilyadreamix.amino.data.dto.objects.Community

@Serializable
data class JoinedCommunitiesResponse(
    @SerialName("communityList")
    val communityList: List<Community>
) : BaseResponse()
