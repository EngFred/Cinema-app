package com.omongole.fred.yomovieapp.presentation.screens.player

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.util.Resource

@Composable
fun ShowsPlayerScreen(
    name: String,
    assistedFactory: ShowsPreviewPlayerScreenViewModel.ShowsPreviewPlayerScreenViewModelAssistedFactory
) {

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
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .height(80.dp)
                                .width(65.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            model = itunes.result[0].artworkUrl100,
                            contentDescription = "Poster Image",
                            contentScale = ContentScale.FillBounds,
                        )
                        Text(text = itunes.result[0].trackCensoredName)
                    }
                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        VideoPlayer(videoUrl = videoPreviewUrl )
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ) {
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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center ) {
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