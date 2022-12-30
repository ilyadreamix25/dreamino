package ua.ilyadreamix.amino.signin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ua.ilyadreamix.amino.signin.ui.SignInScreen
import ua.ilyadreamix.amino.core.theme.AminoTheme

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