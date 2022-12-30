package ua.ilyadreamix.amino.http.core

import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer

// class HttpInterceptor : Interceptor {
//     override fun intercept(chain: Interceptor.Chain): Response {
//         val request = chain.request()
//         val builder = request.newBuilder()
//
//         if (request.method == "POST") {
//             request.body?.let {
//
//             }
//         }
//     }
//
//     private fun getBodyBytes(body: RequestBody): ByteArray {
//         val buffer = Buffer()
//         body.writeTo(buffer)
//
//         val bodyBytes = buffer.readByteArray()
//         buffer.close()
//
//         return bodyBytes
//     }
// }