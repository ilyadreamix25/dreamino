package ua.ilyadreamix.amino.data.utility

import io.ktor.content.*

object BodyUtility {
    fun getBodyBytes(body: Any): ByteArray {
        val bodyType = try {
            body as ByteArrayContent
            "bytes"
        } catch (_: ClassCastException) {
            body as TextContent
            "text"
        }

        return when (bodyType) {
            "bytes" -> (body as ByteArrayContent).bytes()
            else -> (body as TextContent).bytes()
        }
    }
}