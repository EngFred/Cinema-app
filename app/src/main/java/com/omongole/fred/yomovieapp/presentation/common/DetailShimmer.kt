package com.omongole.fred.yomovieapp.presentation.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omongole.fred.yomovieapp.presentation.theme.YoMovieAppTheme

@Composable
fun AnimatedDetailShimmerEffect() {

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 400f,
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

    DetailShimmerItem(brush = brush )
}

@Composable
fun DetailShimmerItem(brush: Brush ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .height(52.dp)
                .padding(10.dp)
                .background(brush)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Row( modifier = Modifier.fillMaxWidth() ) {
            Spacer(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .weight(1f)
                    .height(400.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(brush),
            )
            Spacer(modifier = Modifier.size(10.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(400.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 7.dp)
                        .height(22.dp)
                        .background(brush)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(22.dp)
                        .background(brush)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(22.dp)
                        .background(brush)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(22.dp)
                        .background(brush)
                )
                Spacer(modifier = Modifier.size(20.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 7.dp)
                        .height(22.dp)
                        .background(brush)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(22.dp)
                        .background(brush)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(22.dp)
                        .background(brush)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .height(22.dp)
                        .background(brush)
                )
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .padding(start = 10.dp)
                .height(22.dp)
                .background(brush)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Divider()
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(.3f)
                    .height(22.dp)
                    .padding(end = 10.dp)
                    .background(brush)
            )
            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .width(2.dp)
                    .background(color = Color.Gray)
                    .padding(10.dp)
                    .background(brush)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .height(22.dp)
                    .padding(start = 10.dp, end = 10.dp)
                    .background(brush)
            )
            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .width(2.dp)
                    .background(color = Color.Gray)
                    .padding(10.dp)
                    .background(brush)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(.6f)
                    .height(22.dp)
                    .padding(start = 10.dp)
                    .background(brush)
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Divider()
        Spacer(modifier = Modifier.size(10.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .height(22.dp)
                .padding(start = 10.dp)
                .background(brush)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(.4f)
                    .height(22.dp)
                    .background(brush)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .height(22.dp)
                    .background(brush)
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .height(82.dp)
                .background(brush)
        )
    }
}

@Preview( showBackground = true )
@Composable
fun Prev() {
    YoMovieAppTheme {
        DetailShimmerItem(brush =
            Brush.linearGradient(
                colors = listOf(
                    Color.LightGray.copy(alpha = 0.6f),
                    Color.LightGray.copy(alpha = 0.2f),
                    Color.LightGray.copy(alpha = 0.6f),
                )
            )
        )
    }
}
