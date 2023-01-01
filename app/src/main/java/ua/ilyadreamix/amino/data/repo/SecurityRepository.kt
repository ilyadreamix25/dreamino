package ua.ilyadreamix.amino.data.repo

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ua.ilyadreamix.amino.data.dto.security.SendPublicKeyRequest
import ua.ilyadreamix.amino.data.module.AminoModule

object SecurityRepository {
    suspend fun sendPublicKey(
        body: SendPublicKeyRequest
    ): HttpResponse = AminoModule.getClient()
        .post("g/s/security/public_key") {
            setBody(body)
            contentType(ContentType.Application.Json)
        }
}