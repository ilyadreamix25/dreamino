package ua.ilyadreamix.amino.ui.community.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import ua.ilyadreamix.amino.ui.community.CommunityViewModel

@Composable
fun FeedScreen(
    viewModel: CommunityViewModel,
    listState: LazyListState
) {
    val featuredState = viewModel.featuredState.value
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)

    featuredState.data?.let { featuredPosts ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp),
            state = listState
        ) {
            item {
                FeaturedPostItem(
                    shimmerInstance = shimmer,
                    featuredPost = featuredPosts.featuredList[0],
                    small = false
                )
            }

            val featuredPostsWithoutFirstPost = featuredPosts.featuredList.toMutableList()
            featuredPostsWithoutFirstPost.removeAt(0)

            items(
                items = featuredPostsWithoutFirstPost.chunked(2),
                key = { posts -> posts.hashCode() }
            ) { featuredPosts ->
                Row {
                   featuredPosts.forEach { featuredPost ->
                       if (featuredPosts.size == 2)
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
}