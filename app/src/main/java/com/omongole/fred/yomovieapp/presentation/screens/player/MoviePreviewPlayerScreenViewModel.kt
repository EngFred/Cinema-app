package com.omongole.fred.yomovieapp.presentation.screens.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.omongole.fred.yomovieapp.data.modals.Itune
import com.omongole.fred.yomovieapp.domain.usecases.itunes.GetItuneMoviesUseCase
import com.omongole.fred.yomovieapp.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MoviePreviewPlayerScreenViewModel @AssistedInject constructor(
    private val getItuneMoviesUseCase: GetItuneMoviesUseCase,
    @Assisted private val name: String
) : ViewModel() {

    private val _movieItunes = MutableStateFlow<Resource<List<Itune>>>(Resource.Loading)
    val movieItunes = _movieItunes.asStateFlow()

    init {
        getItuneMovies()
    }

    fun getItuneMovies() {
        Resource.Loading
        viewModelScope.launch {
            getItuneMoviesUseCase.invoke(name)
                .catch {
                    _movieItunes.value = Resource.Error( it.localizedMessage!! )
                }
                .collectLatest {
                _movieItunes.value = Resource.Success(it)
            }
        }
    }


@Suppress("UNCHECKED_CAST")
class MoviePreviewPlayerScreenViewModelFactory(
    private val name: String,
    private val assistedFactory: MoviePreviewPlayerScreenViewModelAssistedFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create( name ) as T
    }
}

@AssistedFactory
interface MoviePreviewPlayerScreenViewModelAssistedFactory {
    fun create( name: String ) : MoviePreviewPlayerScreenViewModel
}}

