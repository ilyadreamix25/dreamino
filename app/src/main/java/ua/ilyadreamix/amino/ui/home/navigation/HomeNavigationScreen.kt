package ua.ilyadreamix.amino.ui.home.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Widgets
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ua.ilyadreamix.amino.R

sealed class HomeNavigationScreen(
    val route: String,
    val title: @Composable () -> Unit = {},
    val inactiveIcon: @Composable () -> Unit = {},
    val activeIcon: @Composable () -> Unit = {}
) {
    object Chats : HomeNavigationScreen(
        route = "chats",
        title = {
            Text(text = stringResource(id = R.string.chats))
        },
        inactiveIcon = {
            Icon(
                imageVector = Icons.Outlined.Forum,
                contentDescription = null
            )
        },
        activeIcon = {
            Icon(
                imageVector = Icons.Filled.Forum,
                contentDescription = null
            )
        }
    )

    object Communities : HomeNavigationScreen(
        route = "communities",
        title = {
            Text(text = stringResource(id = R.string.communities))
        },
        inactiveIcon = {
            Icon(
                imageVector = Icons.Outlined.Widgets,
                contentDescription = null
            )
        },
        activeIcon = {
            Icon(
                imageVector = Icons.Filled.Widgets,
                contentDescription = null
            )
        }
    )

    object Profile : HomeNavigationScreen(
        route = "profile",
        title = {
            Text(text = stringResource(id = R.string.profile))
        },
        inactiveIcon = {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = null
            )
        },
        activeIcon = {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = null
            )
        }
    )

    companion object {
        fun asList() = listOf(Chats, Communities, Profile)
    }
}