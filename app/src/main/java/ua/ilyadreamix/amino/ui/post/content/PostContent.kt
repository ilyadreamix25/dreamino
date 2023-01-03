package ua.ilyadreamix.amino.ui.post.content

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.jsonPrimitive
import ua.ilyadreamix.amino.R
import ua.ilyadreamix.amino.data.dto.objects.Author
import ua.ilyadreamix.amino.utility.coil.getGifImageLoader
import ua.ilyadreamix.amino.utility.coil.toSafeUrl
import ua.ilyadreamix.amino.utility.string.fromAminoMarkdownToHTMLMarkdown
import ua.ilyadreamix.amino.utility.string.fromAminoToLocalDate
import ua.ilyadreamix.amino.utility.string.parseHtml
import ua.ilyadreamix.amino.utility.string.toAbbreviatedString

data class PostContentInfo(
    val content: String,
    val mediaList: List<JsonArray>?,
    val author: Author,
    val created: String,
    val likesCount: Int,
    val liked: Boolean
)

@Composable
fun PostContent(info: PostContentInfo) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 0.dp,
            end = 0.dp,
            bottom = 24.dp
        )
    ) {
        val markdownContent = info.content.fromAminoMarkdownToHTMLMarkdown()

        item(key = "header") {
            Row(
                modifier = Modifier.padding(
                    bottom = 32.dp,
                    start = 16.dp,
                    end = 16.dp
                )
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(info.author.icon?.toSafeUrl())
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Crop,
                    imageLoader = getGifImageLoader()
                )

                Column(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 8.dp)
                        .weight(.4f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = info.author.nickname,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = info.created.fromAminoToLocalDate(Locale.current))
                }

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .weight(.2f)
                        .height(48.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            8.dp,
                            Alignment.CenterHorizontally
                        )
                    ) {
                        Icon(
                            imageVector =
                                if (info.liked) Icons.Filled.Favorite
                                else Icons.Filled.FavoriteBorder,
                            contentDescription = null
                        )
                        Text(
                            text = info.likesCount.toDouble().toAbbreviatedString()
                        )
                    }
                }
            }
        }

        item {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                thickness = 1.dp
            )
        }

        items(
            items = markdownContent,
            key = { item -> markdownContent.indexOf(item) }
        ) { markdownLine ->
            CompositionLocalProvider(
                LocalTextStyle provides LocalTextStyle.current.copy(
                    fontSize = 15.sp,
                    lineHeight = 18.sp
                )
            ) {
                when (markdownLine.type) {
                    0 -> Text(
                        text = markdownLine.value,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                    1 -> {
                        val urlArray = remember { mutableStateOf<JsonArray?>(buildJsonArray {}) }
                        val inlineImageUrl = remember { mutableStateOf("") }
                        val typeContentError = remember { mutableStateOf(false) }
                        val imageLoaded = remember { mutableStateOf(false) }

                        try {
                            urlArray.value = info.mediaList
                                ?.find { value ->
                                    if (value.size == 6)
                                        value[3]
                                            .jsonPrimitive
                                            .content == markdownLine.value
                                    else value[value.size - 1]
                                        .jsonPrimitive
                                        .content == markdownLine.value
                                }
                            inlineImageUrl.value = urlArray.value!![1].jsonPrimitive.content
                        } catch (e: Exception) {
                            val stringMediaList = info.mediaList?.joinToString()
                            Log.e(
                                "PostScreen",
                                "Error while parsing inline image url ($stringMediaList)",
                                e
                            )
                            typeContentError.value = true
                        }

                        if (!typeContentError.value)
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(inlineImageUrl.value.toSafeUrl())
                                    .crossfade(!imageLoaded.value)
                                    .build(),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 12.dp,
                                        bottom = 12.dp
                                    )
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.FillWidth,
                                imageLoader = getGifImageLoader(),
                                onSuccess = { imageLoaded.value = true },
                                onError = { imageLoaded.value = true }
                            )
                        else Box(
                            modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    top = 12.dp,
                                    bottom = 12.dp
                                )
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    Alignment.CenterVertically
                                ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Error,
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.content_type_unsupported),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    2 -> Text(
                        text = markdownLine.value.parseHtml(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                    )
                }
            }
        }
    }
}