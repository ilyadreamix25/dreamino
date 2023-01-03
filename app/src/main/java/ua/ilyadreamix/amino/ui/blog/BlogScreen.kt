package ua.ilyadreamix.amino.ui.blog

import android.app.Activity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.ilyadreamix.amino.ui.core.component.AminoTopAppBar
import ua.ilyadreamix.amino.ui.post.PostViewModel
import ua.ilyadreamix.amino.ui.post.content.PostContent
import ua.ilyadreamix.amino.ui.post.content.PostContentInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogScreen(viewModel: PostViewModel) {
    val context = LocalContext.current
    val blogInfo = viewModel.blogState.value.data?.blog

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            AminoTopAppBar(
                title = {
                    Crossfade(
                        targetState = blogInfo == null,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            if (it) Spacer(modifier = Modifier.fillMaxWidth())
                            else Text(
                                text = blogInfo!!.title.trim(),
                                maxLines = 1,
                                fontSize = 18.sp,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(start = 24.dp)
                            )
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
                }
            )
        }
    ) { insets ->
        Crossfade(
            targetState =
                viewModel.blogState.value.isLoading ||
                blogInfo == null,
            modifier = Modifier
                .fillMaxSize()
                .padding(insets)
        ) { loading ->
            Box(modifier = Modifier.fillMaxSize()) {
                if (loading) CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                ) else blogInfo!!.content?.let { content ->
                    PostContent(
                        info = PostContentInfo(
                            content = content,
                            mediaList = blogInfo.mediaList,
                            author = blogInfo.author,
                            created = blogInfo.createdTime.replace("T", " "),
                            likesCount = blogInfo.votesCount,
                            liked = blogInfo.votedValue != 0
                        )
                    )
                }
            }
        }
    }
}