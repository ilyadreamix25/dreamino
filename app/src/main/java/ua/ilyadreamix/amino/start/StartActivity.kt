package ua.ilyadreamix.amino.start

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.ilyadreamix.amino.home.HomeActivity
import ua.ilyadreamix.amino.auth.SignInActivity
import ua.ilyadreamix.amino.core.theme.AminoTheme
import ua.ilyadreamix.amino.session.SessionStatus
import ua.ilyadreamix.amino.session.SessionUtility
import ua.ilyadreamix.amino.start.ui.StartScreen

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

    private fun login(authStatus: Int) {
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