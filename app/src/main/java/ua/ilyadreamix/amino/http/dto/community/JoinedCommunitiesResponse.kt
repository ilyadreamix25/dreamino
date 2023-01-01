package ua.ilyadreamix.amino.http.dto.community

import ua.ilyadreamix.amino.http.dto.core.BaseResponse
import ua.ilyadreamix.amino.http.dto.objects.Community

data class JoinedCommunitiesResponse(
    val communityList: List<Community>
) : BaseResponse()
