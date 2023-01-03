package ua.ilyadreamix.amino.ui.home.communities

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.shimmer
import ua.ilyadreamix.amino.utility.coil.getGifImageLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityItem(
    iconUrl: String,
    coverUrl: String,
    name: String,
    shimmerInstance: Shimmer,
    modifier: Modifier = Modifier,
    iconBorderColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = {}
) {
    var loadingIcon by remember {
        mutableStateOf(true)
    }
    var loadingCover by remember {
        mutableStateOf(true)
    }

    var doShimmer by remember {
        mutableStateOf(true)
    }
    doShimmer = loadingCover && loadingIcon

    Box(
        modifier =
            if (doShimmer) modifier
                .height(180.dp)
                .shimmer(shimmerInstance)
            else modifier.height(180.dp)
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize(),
            elevation = CardDefaults.cardElevation(
                4.dp,
                4.dp,
                4.dp,
                4.dp,
                4.dp
            ),
            onClick = onClick
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(coverUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    onSuccess = {
                        loadingCover = false
                    },
                    contentScale = ContentScale.Crop,
                    imageLoader = getGifImageLoader()
                )
                Text(
                    text = name,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.BottomCenter),
                    fontWeight = FontWeight(1000),
                    fontSize = 13.sp,
                    lineHeight = 12.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = LocalTextStyle.current.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = .75f),
                            offset = Offset(0f, 0f),
                            blurRadius = 5f
                        ),
                        color = Color.White
                    )
                )
            }
        }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .padding(1.dp)
                .clip(RoundedCornerShape(12.dp))
                .align(Alignment.TopStart)
                .size(48.dp)
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = iconBorderColor
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(iconBorderColor),
            onSuccess = {
                loadingIcon = false
            },
            imageLoader = getGifImageLoader()
        )
    }
}