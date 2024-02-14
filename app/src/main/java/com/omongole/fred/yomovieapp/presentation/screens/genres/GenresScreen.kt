package com.omongole.fred.yomovieapp.presentation.screens.genres

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.viewModel.GenresViewModel

@Composable
fun GenresScreen(
    modifier: Modifier,
    fetchMoviesByGenre: ( genreId: Long ) -> Unit,
    fetchShowsByGenre: ( genreId: Long ) -> Unit
) {
    val genresViewModel : GenresViewModel = hiltViewModel()
    val movieGenres = genresViewModel.moviesGenres.collectAsState().value
    val showsGenres = genresViewModel.showsGenres.collectAsState().value

    var id by remember {
        mutableLongStateOf(0L)
    }

    if ( movieGenres.isEmpty() && showsGenres.isEmpty() && genresViewModel.error.isEmpty() ) {
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
                genresViewModel.getShowsGenres()
            } )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "MOVIE GENRES",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(5.dp))
            Divider()
            Spacer(modifier = Modifier.size(10.dp))
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier.height(350.dp)
            ) {
                items( movieGenres ){
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
            Spacer(modifier = Modifier.size(18.dp))
            Text(text = "SHOWS GENRES", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.size(5.dp))
            Divider()
            Spacer(modifier = Modifier.size(10.dp))
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.height(350.dp),
                contentPadding = PaddingValues(5.dp)
            ) {
                items( showsGenres ){
                    OutlinedButton(
                        onClick = {
                            id = it.id
                            fetchShowsByGenre( id ) },
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
}