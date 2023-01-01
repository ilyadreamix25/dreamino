package ua.ilyadreamix.amino.data.repo

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ua.ilyadreamix.amino.data.dto.auth.LoginByEmailRequest
import ua.ilyadreamix.amino.data.module.AminoModule

object AuthRepository {
    suspend fun loginByEmail(
        body: LoginByEmailRequest,
        deviceId: String
    ): HttpResponse = AminoModule.getClient()
        .post("g/s/auth/login") {
            header(AminoModule.Headers.DEVICE_ID_HEADER, deviceId)
            setBody(body)
            contentType(ContentType.Application.Json)
        }
}