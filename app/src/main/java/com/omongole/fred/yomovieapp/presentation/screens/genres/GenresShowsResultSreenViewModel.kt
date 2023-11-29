package com.omongole.fred.yomovieapp.presentation.screens.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.omongole.fred.yomovieapp.domain.usecases.movies.GetMoviesByGenreUseCase
import com.omongole.fred.yomovieapp.domain.usecases.shows.GetShowsByGenreUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GenreShowsResultScreenViewModel @AssistedInject constructor(
    getShowsByGenreUseCase: GetShowsByGenreUseCase,
    @Assisted private val genreId: Long
) : ViewModel() {
    val shows = getShowsByGenreUseCase.invoke( genreId ).cachedIn(viewModelScope)
}


@Suppress("UNCHECKED_CAST")
class GenreShowsResultScreenViewModelFactory(
    private val genreId: Long,
    private val assistedFactory: GenresShowsResultViewModelAssistedFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create( genreId ) as T
    }
}

@AssistedFactory
interface GenresShowsResultViewModelAssistedFactory {
    fun create( genreId: Long ) : GenreShowsResultScreenViewModel
}