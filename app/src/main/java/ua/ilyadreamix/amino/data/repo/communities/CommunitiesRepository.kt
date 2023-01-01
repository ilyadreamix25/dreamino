package ua.ilyadreamix.amino.data.repo.communities

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
}