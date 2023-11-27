package com.omongole.fred.yomovieapp.presentation.screens.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.omongole.fred.yomovieapp.domain.usecases.movies.GetMoviesByGenreUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GenreResultScreenViewModel @AssistedInject constructor(
    getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    @Assisted private val genreId: Long
) : ViewModel() {

    val movies = getMoviesByGenreUseCase.invoke( genreId ).cachedIn(viewModelScope)

}


@Suppress("UNCHECKED_CAST")
class GenreResultScreenViewModelFactory(
    private val genreId: Long,
    private val assistedFactory: GenreResultViewModelAssistedFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create( genreId ) as T
    }
}

@AssistedFactory
interface GenreResultViewModelAssistedFactory {
    fun create( genreId: Long ) : GenreResultScreenViewModel
}