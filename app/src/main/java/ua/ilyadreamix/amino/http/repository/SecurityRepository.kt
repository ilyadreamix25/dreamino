package ua.ilyadreamix.amino.http.repository

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ua.ilyadreamix.amino.http.dto.auth.SendPublicCertificatesRequest
import ua.ilyadreamix.amino.http.dto.core.BaseResponse

interface SecurityRepository {
    @POST("g/s/security/public_key")
    suspend fun sendPublicKey(
        @Body body: SendPublicCertificatesRequest
    ): Response<BaseResponse>
}