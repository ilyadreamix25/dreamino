package ua.ilyadreamix.amino.data.utility

import org.apache.commons.codec.binary.Base64
import ua.ilyadreamix.amino.AminoApplication
import ua.ilyadreamix.amino.utility.keystore.KeyStoreUtility
import java.security.Signature

object NewAminoHashUtility {
    const val DEFAULT_NAME = "00000000-0000-0000-0000-000000000000"

    fun generateSignature(message: ByteArray): String {
        val keyStoreUtility = KeyStoreUtility(AminoApplication.getCertificateName())
        val key = keyStoreUtility.getKey()

        val signature = Signature.getInstance("SHA256withECDSA")
            .apply {
                initSign(key)
                update(message)
            }
        val signedMessage = signature.sign()

        return String(Base64.encodeBase64(signedMessage))
    }
}