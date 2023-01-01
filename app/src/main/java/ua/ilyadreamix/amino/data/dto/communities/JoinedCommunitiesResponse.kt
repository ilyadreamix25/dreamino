package ua.ilyadreamix.amino.data.dto.communities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ua.ilyadreamix.amino.data.dto.core.BaseResponse

@Serializable
data class JoinedCommunitiesResponse(
    @SerialName("communityList")
    val communityList: List<Community>
) : BaseResponse()
