package com.omongole.fred.yomovieapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.omongole.fred.yomovieapp.presentation.theme.MidnightBlack
import com.omongole.fred.yomovieapp.util.displayPosterImage
import kotlinx.coroutines.Dispatchers

@Composable
fun PosterImage(
    imageUrl: String,
    height: Dp = 350.dp,
    width: Dp?,
    scaleType: ContentScale = ContentScale.Crop,
    cornerSize: Dp = 8.dp,
    horizontalPadding: Dp = 2.dp,
    onClick: ((Int) -> Unit)? = null,
    id: Int? = null
) {

    val context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .data(displayPosterImage(imageUrl))
        .dispatcher(Dispatchers.IO)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()

    if ( width == null ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(cornerSize))
                .height(height)
                .background(MidnightBlack),
            model = imageRequest,
            contentDescription = "Poster Image",
            contentScale = scaleType,
        )
    } else {
        AsyncImage(
            modifier = Modifier
                .clickable { onClick?.invoke(id!!) }
                .padding(horizontal = horizontalPadding)
                .clip(RoundedCornerShape(cornerSize))
                .width(width)
                .height(height),
            model = imageRequest,
            contentDescription = "Poster Image",
            contentScale = scaleType,
        )
    }
}