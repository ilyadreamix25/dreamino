package ua.ilyadreamix.amino.http.utility

import org.apache.commons.codec.binary.Base64
import ua.ilyadreamix.amino.http.hash.KeyStoreUtility
import java.security.Signature

object NewAminoHashUtility {
    fun generateSignature(message: ByteArray): String {
        val keyStoreUtility = KeyStoreUtility()
        val key = keyStoreUtility.getKey()

        val signature = Signature.getInstance("SHA256withECDSA")
            .apply {
                initSign(key)
                update(message)
            }
        val signedMessage = signature.sign()

        return Base64.encodeBase64URLSafeString(signedMessage)
    }
}