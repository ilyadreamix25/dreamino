package ua.ilyadreamix.amino.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ua.ilyadreamix.amino.ui.core.theme.AminoTheme
import ua.ilyadreamix.amino.ui.home.ui.HomeScreen

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AminoTheme {
                HomeScreen()
            }
        }
    }
}