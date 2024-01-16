package com.omongole.fred.yomovieapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.presentation.viewModel.SharedViewModel
import com.omongole.fred.yomovieapp.util.Constants

@Composable
fun PosterScreen(
    sharedViewModel: SharedViewModel,
) {
    val posterPath = sharedViewModel.moviePath
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = "${Constants.BASE_IMAGE_URL}${posterPath}",
            contentDescription = "Poster Image",
            contentScale = ContentScale.Fit,
        )
    }
}