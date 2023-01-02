package ua.ilyadreamix.amino.ui.community

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ua.ilyadreamix.amino.ui.community.navigation.CommunityNavigationHost
import ua.ilyadreamix.amino.ui.community.navigation.CommunityScreens
import ua.ilyadreamix.amino.ui.core.component.AminoTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(ndcId: Int) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()

    val communitiesViewModel = viewModel<CommunityViewModel>()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    NavigationDrawerItem(
                        label = { Text(text = "Cookie") },
                        selected = true,
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(12.dp),
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Cookie,
                                contentDescription = null
                            )
                        }
                    )
                }
            },
            gesturesEnabled = true,
            drawerState = drawerState
        ) {
            ObserveCommunity(
                viewModel = communitiesViewModel,
                drawerState = drawerState,
                navController = navController,
                ndcId = ndcId
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObserveCommunity(
    viewModel: CommunityViewModel,
    drawerState: DrawerState,
    navController: NavHostController,
    ndcId: Int
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    var isCommunityTopBarVisible by remember {
        mutableStateOf(true)
    }
    var selectedTabIndex by remember {
        mutableStateOf(1)
    }

    isCommunityTopBarVisible = (
        remember { derivedStateOf { listState.firstVisibleItemIndex } }.value == 0
    )

    if (viewModel.communityState.value.code == -1) {
        viewModel.getCommunityInfo(ndcId)
        viewModel.getFeaturedBlogs(ndcId)
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.systemBarsPadding()
            ) {
                val communityState = viewModel.communityState.value.data

                AnimatedVisibility(isCommunityTopBarVisible) {
                    AminoTopAppBar(
                        title = {
                            Column {
                                Crossfade(
                                    targetState = communityState == null,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Box(modifier = Modifier.fillMaxWidth()) {
                                        if (it) Box(Modifier)
                                        else Text(
                                            text = communityState!!.community.name,
                                            modifier = Modifier.align(Alignment.Center),
                                            maxLines = 1,
                                            fontSize = 18.sp,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    (context as Activity).finish()
                                }
                            ) {
                                // BUG: Icon padding is too small
                                Icon(
                                    imageVector = Icons.Filled.ArrowBackIos,
                                    contentDescription = null,
                                    modifier = Modifier.padding(start = 8.dp)
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }

                AnimatedVisibility(visible = communityState != null) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        indicator = { tabPositions ->  
                            Box(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                    .height(4.dp)
                                    .padding(horizontal = 48.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(50)
                                    )
                            )
                        },
                        divider = {}
                    ) {
                        CommunityScreens.asList().forEach { tabInfo ->
                            Tab(
                                selected = selectedTabIndex.toString() == tabInfo.route,
                                onClick = {
                                    selectedTabIndex = tabInfo.route.toInt()
                                    navController.navigate(tabInfo.route)
                                },
                                text = tabInfo.title
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Filled.Forum,
                    contentDescription = null
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        Crossfade(
            targetState =
                viewModel.communityState.value.isLoading &&
                viewModel.featuredState.value.isLoading,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (it) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                else CommunityNavigationHost(
                    navHostController = navController,
                    viewModel = viewModel,
                    listState = listState
                )
            }
        }
    }
}