package com.omongole.fred.yomovieapp.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.omongole.fred.yomovieapp.presentation.screens.home.components.HeaderSection
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.screens.home.components.AlertDialog
import com.omongole.fred.yomovieapp.presentation.screens.home.components.MoviesSection
import com.omongole.fred.yomovieapp.presentation.viewModel.HomeScreenEvent
import com.omongole.fred.yomovieapp.presentation.viewModel.HomeScreenViewModel

@Composable
fun HomeScreen(
    modifier: Modifier,
    showMovieDetail: (Int) -> Unit,
    darkTheme: Boolean
) {
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()

    var dialogState by remember { mutableStateOf(false) }

    AlertDialog(
        dialogState = dialogState,
        onOkClicked = { dialogState = false },
        onDismiss = { dialogState = false }
    )

    val nowPlayingMovies = homeScreenViewModel.nowPlayingMovies.collectAsLazyPagingItems()
    val popularMovies = homeScreenViewModel.popularMovies.collectAsLazyPagingItems()
    val topRatedMovies = homeScreenViewModel.topRatedMovies.collectAsLazyPagingItems()

    if ( (nowPlayingMovies.loadState.refresh is LoadState.Error) &&
        (topRatedMovies.loadState.refresh is LoadState.Error) &&
        (popularMovies.loadState.refresh is LoadState.Error )
    ){
        NoInternetComponent(
            modifier = modifier.fillMaxSize(),
            error = "Whoops! Something has gone wrong\n Unable to connect to the server.",
            refresh = {
                nowPlayingMovies.refresh()
                topRatedMovies.refresh()
                popularMovies.refresh()
            }
        )
    } else {

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState(), enabled = true)
        ) {

            HeaderSection(onClick = {
                homeScreenViewModel.onEvent(HomeScreenEvent.ThemeToggled(it))
            }, themeMode = darkTheme, infoIconClick = {
                dialogState = true
            })

            MoviesSection(
                movies = nowPlayingMovies,
                sectionTitle = "Now Playing",
                onMovieClick = {
                    showMovieDetail(it)
                }
            )

            MoviesSection(
                movies = popularMovies,
                sectionTitle = "Popular",
                onMovieClick = {
                    showMovieDetail(it)
                }
            )

            MoviesSection(
                movies = topRatedMovies,
                sectionTitle = "Top Rated",
                onMovieClick = {
                    showMovieDetail(it)
                }
            )


            Spacer(modifier = Modifier.size(15.dp))
        }
    }
}