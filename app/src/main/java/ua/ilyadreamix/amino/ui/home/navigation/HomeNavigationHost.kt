package ua.ilyadreamix.amino.ui.home.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.ilyadreamix.amino.ui.home.communities.CommunitiesScreen

@Composable
fun HomeNavigationHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        modifier = modifier,
        startDestination = HomeNavigationScreen.Communities.route
    ) {
        composable(HomeNavigationScreen.Chats.route) {
            // Chats screen
        }
        composable(HomeNavigationScreen.Communities.route) {
            CommunitiesScreen()
        }
        composable(HomeNavigationScreen.Profile.route) {
            // Profile screen
        }
    }
}