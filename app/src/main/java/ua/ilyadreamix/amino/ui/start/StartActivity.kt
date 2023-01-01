package ua.ilyadreamix.amino.ui.start

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.ilyadreamix.amino.ui.home.HomeActivity
import ua.ilyadreamix.amino.ui.core.theme.AminoTheme
import ua.ilyadreamix.amino.utility.session.SessionStatus
import ua.ilyadreamix.amino.utility.session.SessionUtility
import ua.ilyadreamix.amino.ui.auth.SignInActivity

@AndroidEntryPoint
class StartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authStatus = SessionUtility.getStatus()
        Log.d("StartActivity", "Auth status: $authStatus")

        lifecycleScope.launch {
            if (authStatus != SessionStatus.EXPIRED)
                delay(3000)
            login(authStatus)
        }

        setContent {
            AminoTheme {
                StartScreen()
            }
        }
    }

    private fun login(authStatus: SessionStatus) {
        when (authStatus) {
            SessionStatus.NOT_AUTHORIZED -> {
                val intent = Intent(
                    this,
                    SignInActivity::class.java
                )
                startActivity(intent)
            }
            SessionStatus.EXPIRED -> {
                // TODO: Relogin
            }
            SessionStatus.ACTIVE -> {
                val intent = Intent(
                    this,
                    HomeActivity::class.java
                )
                startActivity(intent)
            }
        }

        finish()
    }
}