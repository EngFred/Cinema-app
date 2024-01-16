package com.omongole.fred.yomovieapp.presentation.screens.player

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsPreviewPlayerScreenViewModel
import com.omongole.fred.yomovieapp.util.Resource

@Composable
fun ShowsPlayerScreen(
    name: String,
    assistedFactory: ShowsPreviewPlayerScreenViewModel.ShowsPreviewPlayerScreenViewModelAssistedFactory
) {

    val view = LocalView.current
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme
    val darkTheme = isSystemInDarkTheme()

    DisposableEffect(context) {
        val window = (view.context as Activity).window
        window.statusBarColor = Color.Black.toArgb()
        window.navigationBarColor = Color.Black.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        onDispose {
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val viewModel =  viewModel(
        modelClass = ShowsPreviewPlayerScreenViewModel::class.java,
        factory = ShowsPreviewPlayerScreenViewModel.ShowsPreviewPlayerScreenViewModelFactory(
            name, assistedFactory
        )
    )
    val itunes = viewModel.ituneShows.collectAsState().value

    when( itunes ) {
        is Resource.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ) {
                CircularProgressIndicator()
            }
        }
        is Resource.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ) {
                Text(
                    text = itunes.message,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        is Resource.Success -> {
            if ( itunes.result.isNotEmpty() ) {
                val videoPreviewUrl = itunes.result[0].previewUrl
                if ( videoPreviewUrl != null ) {
                    Log.d("PLAYER SCREEN", "Playing ${itunes.result[0]}!")
                    VideoPlayer(videoUrl = videoPreviewUrl )
                } else {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black), contentAlignment = Alignment.Center ) {
                        Text(
                            text = "Video preview not available!",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Red,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            } else {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black), contentAlignment = Alignment.Center ) {
                    Text(
                        text = "Video preview not available!",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Red,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

}