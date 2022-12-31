package ua.ilyadreamix.amino.http.interceptor

import okhttp3.*
import ua.ilyadreamix.amino.session.SessionUtility

class SessionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        val session = SessionUtility.getSessionInfo()
        session?.let {
            builder.addHeader(
                "NDCDEVICEID",
                it.deviceId
            )
            builder.addHeader(
                "NDCAUTH",
                "sid=" + it.sessionId
            )
        }

        return chain.proceed(builder.build())
    }
}