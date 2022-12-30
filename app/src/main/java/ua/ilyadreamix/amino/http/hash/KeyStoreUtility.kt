package ua.ilyadreamix.amino.http.hash

import android.icu.util.GregorianCalendar
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import org.apache.commons.codec.binary.Base64
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.cert.Certificate
import javax.security.auth.x500.X500Principal

class KeyStoreUtility(private val name: String = "auth-keys-uid") {
    fun initKeyPair() {
        val start = GregorianCalendar()
        val end = GregorianCalendar().apply {
            add(GregorianCalendar.YEAR, 10)
        }

        val keyGenBuilder = KeyGenParameterSpec.Builder(name, KeyProperties.PURPOSE_SIGN)
            .apply {
                setCertificateSubject(X500Principal("CN=${name}"))
                setDigests(KeyProperties.DIGEST_SHA256)
                setKeyValidityStart(start.time)
                setKeyValidityEnd(end.time)
                setAttestationChallenge(start.time.toString().toByteArray())
            }

        KeyPairGenerator.getInstance(
            "EC",
            "AndroidKeyStore"
        )
            .apply {
                initialize(keyGenBuilder.build())
                genKeyPair()
            }
    }

    private fun getCertificateChain(): Array<out Certificate> =
        KeyStore.getInstance("AndroidKeyStore")
            .apply { load(null, null) }
            .getCertificateChain(name)

    fun getCertificates() =
        getCertificateChain()
            .map {
                "-----BEGIN CERTIFICATE-----\n" +
                Base64.encodeBase64String(it.encoded)
                    .replace(
                        "(.{64})".toRegex(),
                        "$1\n"
                    ) +
                "\n-----END CERTIFICATE-----"
            }
}