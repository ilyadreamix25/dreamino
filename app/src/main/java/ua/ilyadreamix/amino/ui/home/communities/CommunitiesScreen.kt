package ua.ilyadreamix.amino.ui.home.communities

import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import ua.ilyadreamix.amino.ui.community.CommunityActivity

@Composable
fun CommunitiesScreen() {
    val communitiesViewModel = viewModel<CommunitiesViewModel>()
    val communitiesState = rememberLazyGridState()

    val context = LocalContext.current

    if (communitiesViewModel.joinedState.value.code == -1)
        communitiesViewModel.getJoinedCommunities()

    Crossfade(
        targetState = communitiesViewModel.joinedState.value.isLoading,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (it)
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            else {
                val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
                communitiesViewModel.joinedState.value.data?.communityList?.let { communities ->
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(8.dp),
                        state = communitiesState
                    ) {
                        items(communities, key = { item -> item.hashCode() }) { community ->
                            community.mediaList?.let { mediaList ->
                                CommunityItem(
                                    iconUrl = community.icon
                                        .replace("http", "https"),
                                    coverUrl = mediaList[0]
                                        .jsonArray[1]
                                        .jsonPrimitive
                                        .content
                                        .replace("http", "https"),
                                    name = community.name,
                                    shimmerInstance = shimmer,
                                    onClick = {
                                        val intent = Intent(context, CommunityActivity::class.java)
                                        intent.putExtra("ndcId", community.ndcId)
                                        context.startActivity(intent)
                                    },
                                    modifier = Modifier.padding(3.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}