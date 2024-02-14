package com.omongole.fred.yomovieapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.omongole.fred.yomovieapp.presentation.viewModel.SharedViewModel
import com.omongole.fred.yomovieapp.util.Constants
import com.omongole.fred.yomovieapp.util.displayOriginalImage

@Composable
fun PosterScreen(
    sharedViewModel: SharedViewModel,
) {
    val posterPath = sharedViewModel.moviePath

    Box(
        modifier = Modifier
            .fillMaxSize()
            //.background(MaterialTheme.colorScheme.background)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = displayOriginalImage(posterPath),
            contentDescription = "Poster Image",
            contentScale = ContentScale.Fit,
        )
    }
}