package com.omongole.fred.yomovieapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.omongole.fred.yomovieapp.domain.modals.TvShow
import com.omongole.fred.yomovieapp.domain.usecases.shows.SearchTvShowsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ShowsSearchResultScreenViewModel @AssistedInject constructor(
    private val searchTvShowsUseCase: SearchTvShowsUseCase,
    @Assisted private val query: String
) : ViewModel() {

    private val _shows = MutableStateFlow<PagingData<TvShow>>(PagingData.empty())
    val shows = _shows.asStateFlow()

    init {
        getShows(query)
    }


    private fun getShows( query: String ) {
        viewModelScope.launch {
            searchTvShowsUseCase.invoke(query)
                .cachedIn(viewModelScope)
                .collectLatest{
                _shows.value = it
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class ShowsSearchResultScreenViewModelFactory(
    private val query: String,
    private val assistedFactory: ShowsSearchResultScreenViewModelAssistedFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create( query ) as T
    }
}

@AssistedFactory
interface ShowsSearchResultScreenViewModelAssistedFactory {
    fun create( query: String ) : ShowsSearchResultScreenViewModel
}

