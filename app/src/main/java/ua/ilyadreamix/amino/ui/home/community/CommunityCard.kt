package ua.ilyadreamix.amino.ui.home.community

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import ua.ilyadreamix.amino.ui.core.theme.AminoTheme

@Composable
fun RowScope.CommunityCard(
    iconUrl: String,
    coverUrl: String,
    name: String,
    shimmerInstance: Shimmer,
    modifier: Modifier = Modifier,
    iconBorderColor: Color = MaterialTheme.colorScheme.primary,
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
                .weight(3f)
                .shimmer(shimmerInstance)
            else modifier
                .height(180.dp)
                .weight(3f)
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
            )
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
                    contentScale = ContentScale.Crop
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
            }
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun CommunityCardPreview() {
    val previewIconUrl =
        "https://cm1.narvii.com/" +
        "6656/ba7077272551c7ba216c3006222b83836ce3170c_120.jpg"
    val previewCoverUrl =
        "https://cm1.narvii.com/" +
        "7199/891fcfc315922dd1cf35ef9b52513d37d0ca15bd_188.jpg"
    val previewName = "Sport Amino EN"

    AminoTheme {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)
            repeat(3) {
                CommunityCard(previewIconUrl, previewCoverUrl, previewName, shimmerInstance)
            }
        }
    }
}