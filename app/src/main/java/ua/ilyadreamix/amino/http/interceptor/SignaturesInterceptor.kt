package ua.ilyadreamix.amino.http.interceptor

import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import ua.ilyadreamix.amino.http.utility.NewAminoHashUtility
import ua.ilyadreamix.amino.http.utility.OldAminoHashUtility

class SignaturesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        if (request.method == "POST") {
            request.body?.let {
                val bodyBytes = getBodyBytes(it)

                builder.addHeader(
                    "NDC-MESSAGE-SIGNATURE",
                    NewAminoHashUtility.generateSignature(bodyBytes)
                )
                builder.addHeader(
                    "NDC-MSG-SIG",
                    OldAminoHashUtility.generateSignature(bodyBytes)
                )
            }
        }

        return chain.proceed(builder.build())
    }

    private fun getBodyBytes(body: RequestBody): ByteArray {
        val buffer = Buffer()
        body.writeTo(buffer)

        val bodyBytes = buffer.readByteArray()
        buffer.close()

        return bodyBytes
    }
}