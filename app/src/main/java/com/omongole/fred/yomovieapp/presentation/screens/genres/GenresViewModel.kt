package com.omongole.fred.yomovieapp.presentation.screens.genres

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omongole.fred.yomovieapp.data.modals.Genre
import com.omongole.fred.yomovieapp.domain.usecases.movies.GetMoviesGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenresViewModel @Inject constructor(
    private val getMoviesGenreUseCase: GetMoviesGenreUseCase
) : ViewModel() {

    private val _genres = MutableStateFlow<List<Genre>>(emptyList())
    val genres = _genres.asStateFlow()

    var error by mutableStateOf("")

    init {
        getMovieGenres()
    }

    fun getMovieGenres() {
        viewModelScope.launch {
            getMoviesGenreUseCase.invoke()
                .catch {
                    error = it.localizedMessage!!
                }
                .collectLatest {
                    _genres.value = it
                }
        }
    }
}