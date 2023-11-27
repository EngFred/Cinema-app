package com.omongole.fred.yomovieapp.presentation.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.omongole.fred.yomovieapp.data.remote.ShowDetailResponse
import com.omongole.fred.yomovieapp.domain.usecases.shows.GetShowDetailUseCase
import com.omongole.fred.yomovieapp.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ShowDetailScreenViewModel @AssistedInject constructor(
    private val getShowDetailUseCase: GetShowDetailUseCase,
    @Assisted private val id: Int
) : ViewModel() {

    private val _show = MutableStateFlow<Resource<ShowDetailResponse>>(Resource.Loading)
    val show = _show.asStateFlow()

    init {
        getShowDetail(id)
    }

    fun getShowDetail( showId: Int ) {
        Resource.Loading
        viewModelScope.launch {
            getShowDetailUseCase.invoke(showId)
                .catch {
                    _show.value = Resource.Error( it.localizedMessage!! )
                }
                .collectLatest {
                _show.value = Resource.Success(it)
            }
        }
    }

}

@Suppress("UNCHECKED_CAST")
class ShowDetailScreenViewModelFactory(
    private val id: Int,
    private val assistedFactory: ShowDetailScreenViewModelAssistedFactory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create( id ) as T
    }
}

@AssistedFactory
interface ShowDetailScreenViewModelAssistedFactory {
    fun create( id: Int ) : ShowDetailScreenViewModel
}

