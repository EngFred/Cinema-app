package com.omongole.fred.yomovieapp.presentation.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedSearchResultShimmerEffect() {

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(translateAnimation.value, translateAnimation.value)
    )

    SearchResultShimmerItem(brush = brush )
}

@Composable
fun SearchResultShimmerItem(brush: Brush ) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .padding(horizontal = 6.dp, vertical = 3.dp)
                .height(115.dp)
                .width(97.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(brush)
        )
        Column(
            modifier = Modifier
                .padding(vertical = 18.dp, horizontal = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
//            Spacer(modifier = Modifier.size(7.dp))
            Spacer(
                modifier = Modifier
                    .height(22.dp)
                    .fillMaxWidth(.9f)
                    .clip(RoundedCornerShape(3.dp))
                    .background(brush)
            )
            Spacer(modifier = Modifier.size(7.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                        .padding(end = 10.dp)
                        .fillMaxWidth(.4f)
                        .clip(RoundedCornerShape(3.dp))
                        .background(brush)
                )
                Spacer(
                    modifier = Modifier
                        .height(24.dp)
                        .width(2.dp)
                        .background(brush)
                )
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                        .padding(start = 10.dp)
                        .fillMaxWidth(.5f)
                        .clip(RoundedCornerShape(3.dp))
                        .background(brush)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultShimmerPreview() {
    SearchResultShimmerItem(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.LightGray.copy(alpha = 0.6f),
                Color.LightGray.copy(alpha = 0.2f),
                Color.LightGray.copy(alpha = 0.6f),
            )
        )
    )
}