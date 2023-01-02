package ua.ilyadreamix.amino.ui.community.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ua.ilyadreamix.amino.R

sealed class CommunityScreens(
    val route: String,
    val title: @Composable () -> Unit
) {
    object Latest : CommunityScreens(
        route = "0",
        title = {
            Text(text = stringResource(id = R.string.latest))
        }
    )

    object Home : CommunityScreens(
        route = "1",
        title = {
            Text(text = stringResource(id = R.string.featured))
        }
    )

    object Chats : CommunityScreens(
        route = "2",
        title = {
            Text(text = stringResource(id = R.string.chats))
        }
    )

    companion object {
        fun asList() = listOf(Latest, Home, Chats)
    }
}
