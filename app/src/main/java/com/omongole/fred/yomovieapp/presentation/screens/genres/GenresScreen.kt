package com.omongole.fred.yomovieapp.presentation.screens.genres

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omongole.fred.yomovieapp.presentation.components.NoInternetComponent

@Composable
fun GenresScreen(
    modifier: Modifier,
    fetchMoviesByGenre: ( genreId: Long ) -> Unit
) {
    val genresViewModel : GenresViewModel = hiltViewModel()
    val genres = genresViewModel.genres.collectAsState().value

    var id by remember {
        mutableStateOf(0L)
    }

    if ( genres.isEmpty() && genresViewModel.error.isEmpty() ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else if ( genresViewModel.error.isNotEmpty() ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoInternetComponent( modifier = modifier, error = genresViewModel.error, refresh = {
                genresViewModel.error = ""
                genresViewModel.getMovieGenres()
            } )
        }
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = modifier,
            contentPadding = PaddingValues(5.dp)
        ) {
            items( genres ){
                OutlinedButton(
                    onClick = {
                        id = it.id
                        fetchMoviesByGenre( id ) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 7.dp)
                ) {
                    Text(text = it.name)
                }
            }
        }
    }
}