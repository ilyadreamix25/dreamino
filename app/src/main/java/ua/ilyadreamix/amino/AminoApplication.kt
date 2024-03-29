package ua.ilyadreamix.amino

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ua.ilyadreamix.amino.utility.keystore.KeyStoreUtility
import ua.ilyadreamix.amino.data.utility.NewAminoHashUtility
import ua.ilyadreamix.amino.utility.session.SessionUtility

@HiltAndroidApp
class AminoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        SessionUtility.initContext(this)
        KeyStoreUtility(getCertificateName()).initKeyPair()
    }

    companion object {
        fun getCertificateName(): String =
            "auth-keys-" + (
                SessionUtility.getSessionInfo()?.userId ?: NewAminoHashUtility.DEFAULT_NAME
            )
    }
}