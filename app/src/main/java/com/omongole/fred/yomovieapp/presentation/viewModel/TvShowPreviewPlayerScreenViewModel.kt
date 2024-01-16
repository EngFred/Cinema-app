package com.omongole.fred.yomovieapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.omongole.fred.yomovieapp.domain.modals.Itune
import com.omongole.fred.yomovieapp.domain.usecases.itunes.GetItuneTvShowsUseCase
import com.omongole.fred.yomovieapp.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ShowsPreviewPlayerScreenViewModel @AssistedInject constructor(
    private val getItuneTvShowsUseCase: GetItuneTvShowsUseCase,
    @Assisted private val name: String
) : ViewModel() {

    private val _ituneShows = MutableStateFlow<Resource<List<Itune>>>(Resource.Loading)
    val ituneShows = _ituneShows.asStateFlow()

    init {
        getItuneMovies()
    }

    fun getItuneMovies() {
        Resource.Loading
        viewModelScope.launch {
            getItuneTvShowsUseCase.invoke(name)
                .catch {
                    _ituneShows.value = Resource.Error( it.localizedMessage!! )
                }
                .collectLatest {
                _ituneShows.value = Resource.Success(it)
            }
        }
    }


@Suppress("UNCHECKED_CAST")
class ShowsPreviewPlayerScreenViewModelFactory(
    private val name: String,
    private val assistedFactory: ShowsPreviewPlayerScreenViewModelAssistedFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create( name ) as T
    }
}

@AssistedFactory
interface ShowsPreviewPlayerScreenViewModelAssistedFactory {
    fun create( name: String ) : ShowsPreviewPlayerScreenViewModel
}}

