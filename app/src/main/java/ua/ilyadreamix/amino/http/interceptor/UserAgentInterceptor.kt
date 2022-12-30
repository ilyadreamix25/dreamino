package ua.ilyadreamix.amino.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ua.ilyadreamix.amino.http.utility.OldAminoHashUtility

class UserAgentInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        builder.addHeader("User-Agent", OldAminoHashUtility.generateUserAgent())

        return chain.proceed(builder.build())
    }
}