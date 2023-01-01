package ua.ilyadreamix.amino.data.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.kotlinx.serializer.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import ua.ilyadreamix.amino.http.utility.NewAminoHashUtility
import ua.ilyadreamix.amino.http.utility.OldAminoHashUtility
import ua.ilyadreamix.amino.data.utility.BodyUtility
import ua.ilyadreamix.amino.utility.session.SessionUtility

@Module
@InstallIn(SingletonComponent::class)
object AminoModule {
    @Provides
    fun getClient() = HttpClient(Android) {
        defaultRequest {
            // Base URL
            url(ApiUrls.NARVII_SERVICE_URL)

            // Auth headers
            headers {
                SessionUtility.getSessionInfo()?.let {
                    append(Headers.DEVICE_ID_HEADER, it.deviceId)
                    append(Headers.SID_HEADER, "sid=${it.sessionId}")
                }
            }
        }

        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.HEADERS
            filter { request ->
                request.method == HttpMethod.Post
            }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }
        .apply {
            // Signature headers
            plugin(HttpSend).intercept { request ->
                if (request.method == HttpMethod.Post) {
                    val bodyBytes = BodyUtility.getBodyBytes(request.body)

                    val newSignature = NewAminoHashUtility.generateSignature(bodyBytes)
                    val oldSignature = OldAminoHashUtility.generateSignature(bodyBytes)

                    request.header(Headers.NEW_SIGNATURE_HEADER, newSignature)
                    request.header(Headers.OLD_SIGNATURE_HEADER, oldSignature)
                }

                execute(request)
            }
        }

    private object ApiUrls {
        const val NARVII_SERVICE_URL = "https://service.narvii.com/api/v1/"
    }

    private object Headers {
        const val NEW_SIGNATURE_HEADER = "NDC-MESSAGE-SIGNATURE"
        const val OLD_SIGNATURE_HEADER = "NDC-MSG-SIG"
        const val DEVICE_ID_HEADER = "NDCDEVICEID"
        const val SID_HEADER = "NDCAUTH"
    }
}