package ua.ilyadreamix.amino.http.hash

import android.icu.util.GregorianCalendar
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import org.apache.commons.codec.binary.Base64
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.cert.Certificate
import javax.security.auth.x500.X500Principal

class KeyStoreUtility(private val name: String = "auth-keys-uid") {
    fun initKeyPair() {
        val start = GregorianCalendar()
        val end = GregorianCalendar()
            .apply {
                add(GregorianCalendar.YEAR, 10)
            }

        val keyGenBuilder = KeyGenParameterSpec.Builder(name, KeyProperties.PURPOSE_SIGN)
            .apply {
                setCertificateSubject(X500Principal("CN=$name"))
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

    fun getKey(key: String? = name, password: String? = null) =
        KeyStore.getInstance("AndroidKeyStore")
            .apply { load(null, null) }
            .getKey(key, password?.toCharArray()) as PrivateKey

    fun getCertificates() =
        getCertificateChain()
            .map {
                val b64Certificate = Base64.encodeBase64String(it.encoded)
                val indentedCertificate = b64Certificate.replace(
                    "(.{64})".toRegex(),
                    "$1\n"
                )

                "-----BEGIN CERTIFICATE-----\n" +
                indentedCertificate +
                "\n-----END CERTIFICATE-----"
            }

    private fun getCertificateChain(): Array<out Certificate> =
        KeyStore.getInstance("AndroidKeyStore")
            .apply { load(null, null) }
            .getCertificateChain(name)
}