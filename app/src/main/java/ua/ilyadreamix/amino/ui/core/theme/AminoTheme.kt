package ua.ilyadreamix.amino.ui.core.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat

@Stable private val DarkColors = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    tertiary = TertiaryDark
)
@Stable private val LightColors = lightColorScheme(
    primary = PrimaryLight,
    secondary = SecondaryLight,
    tertiary = TertiaryLight
)

@Composable
fun AminoTheme(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val view = LocalView.current

    // Dynamic colors are available
    // if SDK >= 31 (Android 12)
    val applyDynamicColors = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
    val setDarkTheme = isSystemInDarkTheme()

    val colorScheme = when {
        applyDynamicColors && setDarkTheme -> dynamicDarkColorScheme(context)
        applyDynamicColors && !setDarkTheme -> dynamicLightColorScheme(context)
        setDarkTheme -> DarkColors
        else -> LightColors
    }

    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window
                .apply {
                    statusBarColor = Color.Transparent.toArgb()
                    navigationBarColor = Color.Transparent.toArgb()
                }

            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat.getInsetsController(window, view)
                .apply {
                    isAppearanceLightNavigationBars = !setDarkTheme
                    isAppearanceLightStatusBars = !setDarkTheme
                }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}