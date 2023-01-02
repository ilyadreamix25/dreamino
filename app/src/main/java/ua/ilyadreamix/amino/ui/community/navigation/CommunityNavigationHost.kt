package ua.ilyadreamix.amino.ui.community.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.ilyadreamix.amino.ui.community.CommunityViewModel
import ua.ilyadreamix.amino.ui.community.feed.FeedScreen

@Composable
fun CommunityNavigationHost(
    navHostController: NavHostController,
    viewModel: CommunityViewModel,
    modifier: Modifier = Modifier,
    listState: LazyListState
) {
    NavHost(
        navController = navHostController,
        modifier = modifier,
        startDestination = CommunityScreens.Home.route
    ) {
        composable(CommunityScreens.Latest.route) {

        }
        composable(CommunityScreens.Home.route) {
            FeedScreen(viewModel = viewModel, listState = listState)
        }
        composable(CommunityScreens.Chats.route) {

        }
    }
}