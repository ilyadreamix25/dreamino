package ua.ilyadreamix.amino.http.repository

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ua.ilyadreamix.amino.http.dto.auth.SignInRequest
import ua.ilyadreamix.amino.http.dto.auth.SignInResponse

interface AuthRepository {
    @POST("g/s/auth/login")
    suspend fun loginByEmail(
        @Body body: SignInRequest,
        @Header("NDCDEVICEID") deviceId: String
    ): Response<SignInResponse>
}