package ua.ilyadreamix.amino.ui.community.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import ua.ilyadreamix.amino.ui.community.CommunityViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedScreen(
    viewModel: CommunityViewModel,
    listState: LazyListState
) {
    val featuredState = viewModel.featuredState.value
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    val refreshing = remember { mutableStateOf(false) }
    val refreshState = rememberPullRefreshState(
        refreshing = refreshing.value,
        onRefresh = {
            featuredState.data?.featuredList?.let {
                refreshing.value = true
                viewModel.getFeaturedBlogs(it[0].refObject.ndcId) {
                    refreshing.value = false
                }
            }
        }
    )

    featuredState.data?.let { featuredAndPinnedPosts ->
        val featuredPosts = featuredAndPinnedPosts.featuredList.filter { post ->
            post.featuredType == 1
        }
        val pinnedPosts = featuredAndPinnedPosts.featuredList.filter { post ->
            post.featuredType == 2
        }

        Box(modifier = Modifier.pullRefresh(refreshState)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp),
                state = listState
            ) {
                items(
                    items = pinnedPosts,
                    key = { pin -> pin.hashCode() },
                    contentType = { 2 }
                ) { pinnedPost ->
                    FeaturedPinItem(pinnedPost)
                }

                if (featuredPosts.isNotEmpty()) {
                    item {
                        FeaturedPostItem(
                            shimmerInstance = shimmer,
                            featuredPost = featuredPosts[0],
                            small = false
                        )
                    }

                    val featuredPostsWithoutFirstPost = featuredPosts.toMutableList()
                    featuredPostsWithoutFirstPost.removeAt(0)

                    items(
                        items = featuredPostsWithoutFirstPost.chunked(2),
                        key = { posts -> posts.hashCode() },
                        contentType = { 1 }
                    ) { chunkedPosts ->
                        Row {
                            chunkedPosts.forEach { featuredPost ->
                                if (chunkedPosts.size == 2)
                                    FeaturedPostItem(
                                        shimmerInstance = shimmer,
                                        featuredPost = featuredPost,
                                        small = true,
                                        modifier = Modifier.weight(.5f)
                                    )
                                else {
                                    FeaturedPostItem(
                                        shimmerInstance = shimmer,
                                        featuredPost = featuredPost,
                                        small = true,
                                        modifier = Modifier.weight(.5f)
                                    )
                                    Spacer(modifier = Modifier.weight(.5f))
                                }
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = refreshing.value,
                state = refreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .alpha(if (refreshState.progress > .1f) 1f else 0f)
            )
        }
    }
}