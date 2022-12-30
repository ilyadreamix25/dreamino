package ua.ilyadreamix.amino.start

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint
import ua.ilyadreamix.amino.home.HomeActivity
import ua.ilyadreamix.amino.signin.SignInActivity
import ua.ilyadreamix.amino.session.SessionStatus
import ua.ilyadreamix.amino.session.SessionUtility

@AndroidEntryPoint
class StartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionUtility = SessionUtility(this)

        when (sessionUtility.getStatus()) {
            SessionStatus.NOT_AUTHORIZED -> {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
            SessionStatus.EXPIRED -> {
                // TODO: Relogin
            }
            SessionStatus.ACTIVE -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}