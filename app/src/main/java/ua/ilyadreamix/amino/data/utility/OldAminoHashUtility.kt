package ua.ilyadreamix.amino.data.utility

import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.HmacAlgorithms
import org.apache.commons.codec.digest.HmacUtils
import org.apache.commons.lang3.ArrayUtils
import kotlin.random.Random

object OldAminoHashUtility {
    private const val DEVICE_KEY = "AE49550458D8E7C51D566916B04888BFB8B3CA7D"
    private const val SIG_KEY = "EAB4F1B9E3340CD1631EDE3B587CC3EBEDF1AFA9"
    private const val AMINO_VERSION = "3.5.34857"

    fun generateDeviceId(): String {
        val data = ByteArray(20)
        val key = Hex.decodeHex(DEVICE_KEY.toCharArray())

        Random.nextBytes(data)

        val mac = hmacSha1Hex(
            ArrayUtils.addAll(
                Hex.decodeHex("52".toCharArray()),
                *data
            ),
            key
        )

        return ("52" +
                String(Hex.encodeHex(data)) +
                mac).uppercase()
    }

    fun generateSignature(byteData: ByteArray): String {
        val key = Hex.decodeHex(SIG_KEY.toCharArray())

        val mac = hmacSha1Digest(
            byteData, key
        )

        val b64Bytes = Base64.encodeBase64(
            ArrayUtils.addAll(
                Hex.decodeHex("52".toCharArray()),
                *mac
            )
        )

        return String(b64Bytes)
    }

    fun generateUserAgent(): String {
        var systemAgent = System.getProperty("http.agent") as String
        systemAgent = systemAgent.substring(0, systemAgent.length - 1)

        return "$systemAgent; com.narvii.amino.master/$AMINO_VERSION)"
    }

    private fun hmacSha1Hex(
        value: ByteArray,
        key: ByteArray
    ): String {
        val hmacUtils = HmacUtils(HmacAlgorithms.HMAC_SHA_1, key)
        val hmacDigest = hmacUtils.hmac(value)

        return String(Hex.encodeHex(hmacDigest))
    }

    private fun hmacSha1Digest(
        value: ByteArray,
        key: ByteArray
    ): ByteArray {
        val hmac = HmacUtils(HmacAlgorithms.HMAC_SHA_1, key)
        return hmac.hmac(value)
    }
}