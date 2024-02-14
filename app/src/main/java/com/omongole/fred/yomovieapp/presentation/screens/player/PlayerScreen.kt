package com.omongole.fred.yomovieapp.presentation.screens.player

import android.app.Activity
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.omongole.fred.yomovieapp.presentation.theme.SeaGreen
import com.omongole.fred.yomovieapp.presentation.viewModel.PlayerScreenViewModel

@OptIn(UnstableApi::class) @Composable
fun PlayerScreen(
    name: String,
    assistedFactory: PlayerScreenViewModel.PlayerViewModelAssistedFactory
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
        modelClass = PlayerScreenViewModel::class.java,
        factory = PlayerScreenViewModel.PlayerViewModelFactory(
            name, assistedFactory
        )
    )

    var lifecycle by rememberSaveable {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event -> lifecycle = event }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when {
        viewModel.uiState.isLoading -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Black), contentAlignment = Alignment.Center ) {
                CircularProgressIndicator()
            }
        }

        viewModel.uiState.error != null -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Black), contentAlignment = Alignment.Center ) {
                Text(
                    text = viewModel.uiState.error!!,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        viewModel.uiState.error == null &&  !viewModel.uiState.isLoading && viewModel.uiState.videoTitle != null -> {
            Box(modifier = Modifier.fillMaxSize()) {
                AndroidView(
                    factory = {
                        PlayerView(it).apply {
                            player = viewModel.player
                            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH
                            setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING)
                        }
                    },
                    update = {
                        when (lifecycle) {
                            Lifecycle.Event.ON_PAUSE -> {
                                it.onPause()
                                it.player?.pause()
                            }

                            Lifecycle.Event.ON_RESUME -> {
                                it.onResume()
                                it.player?.play()
                            }
                            else -> Unit
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                )
                Text(
                    text = viewModel.uiState.videoTitle!!,
                    color = SeaGreen,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.align(Alignment.TopStart).padding(15.dp)
                )
            }
        }

    }
}