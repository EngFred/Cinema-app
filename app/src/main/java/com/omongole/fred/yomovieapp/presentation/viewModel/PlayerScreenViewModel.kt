package com.omongole.fred.yomovieapp.presentation.viewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.HttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.omongole.fred.yomovieapp.MovieApp
import com.omongole.fred.yomovieapp.domain.usecases.itunes.GetItuneMoviesUseCase
import com.omongole.fred.yomovieapp.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class PlayerUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val videoTitle: String? = null
)

@UnstableApi
class PlayerScreenViewModel @AssistedInject constructor(
    private val getItuneMoviesUseCase: GetItuneMoviesUseCase,
    val player: ExoPlayer,
    @Assisted private val name: String
) : ViewModel() {

    var uiState by mutableStateOf(PlayerUiState())
        private set

    companion object {
        val TAG = PlayerScreenViewModel::class.simpleName

        private val httpDataSourceFactory: HttpDataSource.Factory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)

        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(MovieApp.simpleCache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

    init {
        getItuneMovies()
    }

    private fun startPlayer(videoUrl: String ) {

        val videoUri = Uri.parse(videoUrl)
        val mediaItem = MediaItem.fromUri(videoUri)
        val mediaSource = ProgressiveMediaSource.Factory(cacheDataSourceFactory).createMediaSource(mediaItem)

        player.apply {
            setMediaSource( mediaSource, true )
            prepare()
            play()
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    private fun getItuneMovies() {
        Resource.Loading
        viewModelScope.launch {
            getItuneMoviesUseCase.invoke(name)
                .catch {
                    uiState = uiState.copy( error = it.localizedMessage, isLoading = false )
                }
                .collectLatest {
                    if ( it.isEmpty() ) {
                        uiState = uiState.copy( error = "Video not found!", isLoading = false )
                    } else {
                        val videoUrl = it[0].previewUrl
                        val videoTitle = it[0].trackCensoredName
                        if ( videoUrl != null ) {
                            startPlayer(videoUrl)
                            uiState = uiState.copy( error = null, isLoading = false, videoTitle = videoTitle )
                        } else {
                            uiState = uiState.copy( error = "Video not found!", isLoading = false )
                        }
                    }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.stop()
    }

    @Suppress("UNCHECKED_CAST")
class PlayerViewModelFactory(
    private val name: String,
    private val assistedFactory: PlayerViewModelAssistedFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create( name ) as T
    }
}

@AssistedFactory
interface PlayerViewModelAssistedFactory {
    fun create( name: String ) : PlayerScreenViewModel
}}

