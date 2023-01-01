package ua.ilyadreamix.amino.ui.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ua.ilyadreamix.amino.ui.home.HomeTopBar
import ua.ilyadreamix.amino.ui.home.navigation.HomeBottomAppBar
import ua.ilyadreamix.amino.ui.home.navigation.HomeNavigationHost
import ua.ilyadreamix.amino.ui.home.navigation.HomeNavigationScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val navStackBackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navStackBackEntry?.destination
    val currentScreen = HomeNavigationScreen
        .asList()
        .find { it.route == currentDestination?.route }
        ?: HomeNavigationScreen.Communities

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Scaffold(
            bottomBar = { HomeBottomAppBar(navHostController = navController) },
            topBar = {

                val isHomeScreen =
                    currentScreen.route == HomeNavigationScreen.Communities.route

                HomeTopBar(
                    title = currentScreen.title,
                    navigationIcon =
                        if (!isHomeScreen) ({
                            IconButton(
                                onClick = {
                                    navController.navigate(
                                        HomeNavigationScreen.Communities.route
                                    )
                                }
                            ) {
                                // BUG: Icon padding is too small
                                Icon(
                                    imageVector = Icons.Filled.ArrowBackIos,
                                    contentDescription = null,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        }) else ({ /* ... */ }),
                    actions = {
                        IconButton(
                            onClick = { /* TODO */ }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) {
            HomeNavigationHost(
                navHostController = navController,
                modifier = Modifier.padding(it)
            )
        }
    }
}