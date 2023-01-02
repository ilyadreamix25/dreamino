package ua.ilyadreamix.amino.ui.community.feed

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import ua.ilyadreamix.amino.data.dto.objects.Featured
import ua.ilyadreamix.amino.ui.core.component.AminoPin
import ua.ilyadreamix.amino.utility.coil.getGifImageLoader
import ua.ilyadreamix.amino.utility.coil.toSafeUrl

private val ITEM_SHAPE = RoundedCornerShape(16.dp)
private val ITEM_PADDING = PaddingValues(3.dp)

private val SMALL_ITEM_HEIGHT = 275.dp
private val SMALL_ITEM_COVER_HEIGHT = 160.dp
private val BIG_ITEM_HEIGHT = 415.dp
private val BIG_ITEM_COVER_HEIGHT = 280.dp

@Composable
fun FeaturedPostItem(
    shimmerInstance: Shimmer,
    featuredPost: Featured,
    modifier: Modifier = Modifier,
    small: Boolean = false
) {
    val loadingCover = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val refObject = featuredPost.refObject

    Box(
        modifier = modifier
            .padding(ITEM_PADDING)
            .height(
                if (small) SMALL_ITEM_HEIGHT
                else BIG_ITEM_HEIGHT
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            shape = ITEM_SHAPE,
            elevation = CardDefaults.cardElevation(2.dp, 2.dp, 2.dp, 2.dp, 2.dp)
        ) {
            Column {
                val defaultCoverModifierFirst = Modifier
                    .height(
                        if (small) SMALL_ITEM_COVER_HEIGHT
                        else BIG_ITEM_COVER_HEIGHT
                    )
                    .fillMaxWidth()
                val defaultCoverModifierSecond = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onBackground.copy(.2f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))

                // COVER
                // Box for shimmer animation
                Box(
                    modifier =
                        if (loadingCover.value) defaultCoverModifierFirst
                            .shimmer(shimmerInstance)
                            .then(defaultCoverModifierSecond)
                        else defaultCoverModifierFirst.then(defaultCoverModifierSecond)
                ) {
                    refObject.mediaList?.let { mediaList ->
                        val coverUrl = mediaList[0].jsonArray[1].jsonPrimitive.content
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(coverUrl.toSafeUrl())
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            onLoading = { loadingCover.value = true },
                            onSuccess = { loadingCover.value = false },
                            contentScale = ContentScale.Crop,
                            imageLoader = getGifImageLoader()
                        )
                    } ?: run {
                        // mediaList is null ->
                        // No cover
                        // Show error icon
                        Icon(
                            imageVector = Icons.Filled.BrokenImage,
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .alpha(.75f)
                                .align(Alignment.Center)
                        )
                    }

                    if (!small) AminoPin()
                }

                //      | TITLE
                //      | CONTENT or LABEL
                // ICON | LIKE, COMMENT
                Column(
                    modifier = Modifier.padding(
                        if (small) 8.dp
                        else 12.dp
                    )
                ) {
                    val titleTextStyle = LocalTextStyle.current.copy(
                        fontSize =
                            if (small) 15.sp
                            else 17.sp,
                        lineHeight =
                            if (small) 16.sp
                            else 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Column(verticalArrangement = Arrangement.Center) {
                        // Title
                        CompositionLocalProvider(LocalTextStyle provides titleTextStyle) {
                            refObject.title?.let { title ->
                                Text(
                                    text = title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            } ?: refObject.label?.let { label ->
                                Text(
                                    text = label,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        // Content
                        refObject.content?.let {
                            Text(
                                text = it
                                    .replace("""\[[^]]*]""".toRegex(), "")
                                    .replace("\n", " ")
                                    .trim(),
                                fontSize =
                                    if (small) 13.sp
                                    else 15.sp,
                                lineHeight =
                                    if (small) 13.sp
                                    else 15.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Icon
                        refObject.author.icon?.let { iconUrl ->
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(iconUrl.toSafeUrl())
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(
                                        if (small) 32.dp
                                        else 38.dp
                                    )
                                    .clip(CircleShape)
                                    .border(
                                        border = BorderStroke(
                                            1.dp,
                                            MaterialTheme.colorScheme.onBackground
                                        ),
                                        shape = CircleShape
                                    ),
                                onLoading = { loadingCover.value = true },
                                onSuccess = { loadingCover.value = false },
                                contentScale = ContentScale.Crop,
                                imageLoader = getGifImageLoader()
                            )
                        }

                        val actionIconPadding =
                            if (small) 8.dp
                            else 12.dp

                        /*
                         * TODO: Fix likes and comments width
                         * when count is too big
                         */

                        // Likes
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector =
                                    if (refObject.votedValue == 0)
                                        Icons.Filled.FavoriteBorder
                                    else Icons.Filled.Favorite,
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {

                                    }
                                    .padding(
                                        start = actionIconPadding,
                                        end = actionIconPadding - 4.dp
                                    )
                                    .then(
                                        if (small) Modifier.scale(.8f)
                                        else Modifier
                                    )
                            )
                            Text(
                                text = refObject.votesCount.toString(),
                                fontSize =
                                    if (small) 13.sp
                                    else 15.sp
                            )
                        }

                        // Comments
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.QuestionAnswer,
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {

                                    }
                                    .padding(
                                        start = actionIconPadding,
                                        end = actionIconPadding - 4.dp
                                    )
                                    .then(
                                        if (small) Modifier.scale(.8f)
                                        else Modifier
                                    )
                            )
                            Text(
                                text = refObject.commentsCount.toString(),
                                fontSize =
                                    if (small) 13.sp
                                    else 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}