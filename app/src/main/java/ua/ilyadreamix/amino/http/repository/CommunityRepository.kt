package ua.ilyadreamix.amino.http.repository

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CommunityRepository {
    @GET("g/s/community/joined")
    suspend fun getJoinedCommunities(
        @Query("v") v: Int = 1,
        @Query("start") start: Int = 0,
        @Query("size") size: Int = 25
    ): Response<Any>
}