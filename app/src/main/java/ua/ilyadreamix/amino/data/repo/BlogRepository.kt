package ua.ilyadreamix.amino.data.repo

import io.ktor.client.request.*
import io.ktor.client.statement.*
import ua.ilyadreamix.amino.data.module.AminoModule

object BlogRepository {
    suspend fun getBlogInfo(
        ndcId: Int,
        blogId: String
    ): HttpResponse = AminoModule.getClient().get("x$ndcId/s/blog/$blogId")
}