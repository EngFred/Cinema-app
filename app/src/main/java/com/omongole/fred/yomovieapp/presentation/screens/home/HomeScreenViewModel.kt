package com.omongole.fred.yomovieapp.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.omongole.fred.yomovieapp.data.modals.Movie
import com.omongole.fred.yomovieapp.domain.usecases.RetrieveThemeModeUseCase
import com.omongole.fred.yomovieapp.domain.usecases.StoreThemeModeUseCase
import com.omongole.fred.yomovieapp.domain.usecases.movies.GetNowPlayingMoviesUseCase
import com.omongole.fred.yomovieapp.domain.usecases.movies.GetPopularMoviesUseCase
import com.omongole.fred.yomovieapp.domain.usecases.movies.GetTopRatedMoviesUseCase
import com.omongole.fred.yomovieapp.domain.usecases.movies.GetUpComingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getUpComingMoviesUseCase: GetUpComingMoviesUseCase,
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val storeThemeModeUseCase: StoreThemeModeUseCase,
    private val retrieveThemeModeUseCase: RetrieveThemeModeUseCase
) : ViewModel() {

    private val _upComingMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val upComingMovies = _upComingMovies.asStateFlow()

    private val _nowPlayingMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val nowPlayingMovies = _nowPlayingMovies.asStateFlow()

    private val _popularMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val popularMovies = _popularMovies.asStateFlow()

    private val _topRatedMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val topRatedMovies = _topRatedMovies.asStateFlow()

    private val _themeMode = MutableStateFlow(false)
    var themeMode = _themeMode.asStateFlow()


    init {
        retrieveThemeMode()
        getNowPlayingMovies()
        getUpComingMovies()
        getTopRatedMovies()
        getPopularMovies()
    }


    private fun getUpComingMovies() {
        viewModelScope.launch {
            getUpComingMoviesUseCase.invoke()
                .cachedIn(viewModelScope)
                .collectLatest {
                _upComingMovies.value = it
            }
        }
    }
    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            getNowPlayingMoviesUseCase.invoke()
                .cachedIn(viewModelScope)
                .collectLatest {
                _nowPlayingMovies.value = it
            }
        }
    }
    private fun getTopRatedMovies() {
        viewModelScope.launch {
            getTopRatedMoviesUseCase.invoke()
                .cachedIn(viewModelScope)
                .collectLatest {
                _topRatedMovies.value = it
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase.invoke()
                .cachedIn(viewModelScope)
                .collectLatest {
                _popularMovies.value = it
            }
        }
    }

    fun onEvent( event: HomeScreenEvent ) {
        when( event ) {
            is HomeScreenEvent.ThemeToggled -> {
                viewModelScope.launch {
                    storeThemeModeUseCase.invoke( event.themeMode )
                }
            }
        }
    }

    fun retrieveThemeMode() {
        viewModelScope.launch {
            retrieveThemeModeUseCase.invoke().collectLatest {
                _themeMode.value = it
            }
        }
    }

}

sealed class HomeScreenEvent() {
    data class ThemeToggled( val themeMode: Boolean ) : HomeScreenEvent()
}