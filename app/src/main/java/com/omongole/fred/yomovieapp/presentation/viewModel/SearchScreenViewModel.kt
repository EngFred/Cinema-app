package com.omongole.fred.yomovieapp.presentation.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.omongole.fred.yomovieapp.domain.model.movies.Movie
import com.omongole.fred.yomovieapp.domain.usecases.movies.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : ViewModel() {

    var searchQuery by mutableStateOf("")

    private val _trendingMovies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val trendingMovies = _trendingMovies.asStateFlow()

    init {
        getTrendingMovies()
    }

    private fun getTrendingMovies() {
        viewModelScope.launch {
            getTrendingMoviesUseCase.invoke()
                .cachedIn(viewModelScope)
                .collectLatest {
                _trendingMovies.value = it
            }
        }
    }

    fun onEvent(event: SearchScreenEvent) {
        when( event ) {
            is SearchScreenEvent.SearchQueryChange -> {
                searchQuery = event.query
            }
        }
    }
}

sealed class SearchScreenEvent{
    data class SearchQueryChange( val query: String ) : SearchScreenEvent()
}
