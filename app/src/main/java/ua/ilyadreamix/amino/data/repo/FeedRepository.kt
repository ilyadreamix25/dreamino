package ua.ilyadreamix.amino.data.repo

import io.ktor.client.request.*
import io.ktor.client.statement.*
import ua.ilyadreamix.amino.data.module.AminoModule

object FeedRepository {
    suspend fun getFeatured(
        ndcId: Int,
        start: Int = 0,
        size: Int = 25,
        pagingType: String = "t",
    ): HttpResponse = AminoModule.getClient()
        .get("x$ndcId/s/feed/featured") {
            parameter("start", start)
            parameter("size", size)
            parameter("pagingType", pagingType)
        }
}