package com.omongole.fred.yomovieapp.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.omongole.fred.yomovieapp.data.model.shows.ShowDTO
import com.omongole.fred.yomovieapp.domain.model.shows.Show
import com.omongole.fred.yomovieapp.domain.usecases.shows.GetOnAirTvShowsUseCase
import com.omongole.fred.yomovieapp.domain.usecases.shows.GetPopularTvShowsUseCase
import com.omongole.fred.yomovieapp.domain.usecases.shows.GetTopRatedTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel  @Inject constructor(
    private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase,
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val getOnAirTvShowsUseCase: GetOnAirTvShowsUseCase
):  ViewModel() {

    var searchQuery by mutableStateOf("")

    private val _topRatedTvShows = MutableStateFlow<PagingData<Show>>(PagingData.empty())
    val topRatedTvShows = _topRatedTvShows.asStateFlow()

    private val _popularTvShows = MutableStateFlow<PagingData<Show>>(PagingData.empty())
    val popularTvShows = _popularTvShows.asStateFlow()

    private val _onAirTvShows = MutableStateFlow<PagingData<Show>>(PagingData.empty())
    val onAirTvShows = _onAirTvShows.asStateFlow()

    init {
        getOnAirTvShows()
        getTopRatedTvShows()
        getPopularTvShows()
    }


    private fun getTopRatedTvShows() {
        viewModelScope.launch {
            getTopRatedTvShowsUseCase.invoke()
                .cachedIn(viewModelScope)
                .collectLatest {
                _topRatedTvShows.value = it
            }
        }
    }
    private fun getPopularTvShows() {
        viewModelScope.launch {
            getPopularTvShowsUseCase.invoke()
                .cachedIn(viewModelScope)
                .collectLatest {
                _popularTvShows.value = it
            }
        }
    }

    private fun getOnAirTvShows() {
        viewModelScope.launch {
            getOnAirTvShowsUseCase.invoke()
                .cachedIn(viewModelScope)
                .collectLatest {
                _onAirTvShows.value = it
            }
        }
    }

    fun onEvent( event: ShowsScreenEvent) {
        when( event ) {
            is ShowsScreenEvent.SearchQueryChange -> {
                searchQuery = event.query
            }
        }
    }
}

sealed class ShowsScreenEvent{
    data class SearchQueryChange( val query: String ) : ShowsScreenEvent()
}