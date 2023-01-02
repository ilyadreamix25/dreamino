package ua.ilyadreamix.amino.data.repo

import io.ktor.client.request.*
import io.ktor.client.statement.*
import ua.ilyadreamix.amino.data.module.AminoModule

object CommunitiesRepository {
    suspend fun getJoinedCommunities(
        start: Int = 0,
        size: Int = 25,
        v: Int = 1
    ): HttpResponse = AminoModule.getClient()
        .get("g/s/community/joined") {
            parameter("start", start)
            parameter("size", size)
            parameter("v", v)
        }

    suspend fun getCommunityInfo(
        ndcId: Int,
        withInfluencerList: Int = 1,
        withTopicList: Boolean = true,
        influencerListOrderStrategy: String = "fansCount"
    ): HttpResponse = AminoModule.getClient()
        .get("g/s-x${ndcId}/community/info") {
            parameter("withInfluencerList", withInfluencerList)
            parameter("withTopicList", withTopicList)
            parameter("influencerListOrderStrategy", influencerListOrderStrategy)
        }
}