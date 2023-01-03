package ua.ilyadreamix.amino.ui.post

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ua.ilyadreamix.amino.data.types.ObjectTypes
import ua.ilyadreamix.amino.ui.action.ActionExtras
import ua.ilyadreamix.amino.ui.blog.BlogScreen
import ua.ilyadreamix.amino.ui.wiki.WikiScreen

@Composable
fun PostScreen(intent: Intent) {
    val ndcId = intent.getIntExtra(ActionExtras.NDC_ID, 0)
    val postId = intent.getStringExtra(ActionExtras.POST_ID)!!
    val postType = intent.getIntExtra(ActionExtras.POST_TYPE, -1)

    val postViewModel = viewModel<PostViewModel>()
    if (postViewModel.blogState.value.code == -1)
        postViewModel.getBlogInfo(ndcId, postId)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        if (postType == ObjectTypes.BLOG.type) BlogScreen(postViewModel)
        else WikiScreen()
    }
}