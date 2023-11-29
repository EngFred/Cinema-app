package com.omongole.fred.yomovieapp.presentation.screens.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer( videoUrl: String ) {
    val context = LocalContext.current
    val mediaItem = MediaItem.Builder()
        .setUri(videoUrl)
        .build()

    val exoPlayer = remember(context, mediaItem) {
        ExoPlayer.Builder(context)
            .build()
            .also { player ->
                player.setMediaItem(mediaItem)
                player.prepare()
                player.playWhenReady = true
            }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }
    // Use an AndroidView composable to display the video player
    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
            }
        }
    )
}