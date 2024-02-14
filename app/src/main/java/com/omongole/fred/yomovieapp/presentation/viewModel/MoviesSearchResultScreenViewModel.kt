package com.omongole.fred.yomovieapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.omongole.fred.yomovieapp.domain.model.movies.Movie
import com.omongole.fred.yomovieapp.domain.usecases.movies.SearchMoviesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoviesSearchResultScreenViewModel @AssistedInject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    @Assisted private val query: String
) : ViewModel() {

    private val _movies = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val movies = _movies.asStateFlow()

    init {
        getMovies(query)
    }

    private fun getMovies( query: String ) {
        viewModelScope.launch {
            searchMoviesUseCase.invoke(query)
                .cachedIn(viewModelScope)
                .collectLatest {
                _movies.value = it
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class MoviesSearchResultScreenViewModelFactory(
    private val query: String,
    private val assistedFactory: MoviesSearchResultScreenViewModelAssistedFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create( query ) as T
    }
}

@AssistedFactory
interface MoviesSearchResultScreenViewModelAssistedFactory {
    fun create( query: String ) : MoviesSearchResultScreenViewModel
}

