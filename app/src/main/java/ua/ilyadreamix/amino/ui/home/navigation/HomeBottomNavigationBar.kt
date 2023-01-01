package ua.ilyadreamix.amino.ui.home.navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ua.ilyadreamix.amino.ui.core.theme.AminoTheme

@Composable
fun HomeBottomAppBar(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navStackBackEntry by navHostController.currentBackStackEntryAsState()

    val currentDestination = navStackBackEntry?.destination
    val currentScreen = HomeNavigationScreen
        .asList()
        .find { it.route == currentDestination?.route }
        ?: HomeNavigationScreen.Communities

    BottomAppBar(modifier = modifier) {
        HomeNavigationScreen.asList().forEach { screen ->

            val selected =
                currentScreen == screen

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navHostController.navigate(screen.route) {
                        popUpTo(navHostController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                icon =
                    if (selected) screen.activeIcon
                    else screen.inactiveIcon,
                label = screen.title,
                alwaysShowLabel = false
            )
        }
    }
}

@Preview
@Composable
fun HomeBottomAppBarPreview() {
    AminoTheme {
        HomeBottomAppBar(navHostController = rememberNavController())
    }
}