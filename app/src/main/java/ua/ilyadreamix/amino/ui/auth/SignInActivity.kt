package ua.ilyadreamix.amino.ui.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ua.ilyadreamix.amino.ui.core.theme.AminoTheme

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AminoTheme {
                SignInScreen()
            }
        }
    }
}