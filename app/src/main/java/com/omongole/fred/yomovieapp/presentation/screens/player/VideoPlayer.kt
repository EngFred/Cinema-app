package com.omongole.fred.yomovieapp.presentation.screens.player

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING

@OptIn(UnstableApi::class) @Composable
fun VideoPlayer( videoUrl: String ) {

    val context = LocalContext.current

    val exoPlayer = remember{
        val mediaItem = MediaItem.Builder().setUri(videoUrl).build()
        ExoPlayer.Builder(context)
            .build()
            .apply {
                setMediaItem(mediaItem)
                prepare()
                play()
            }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                this.setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}