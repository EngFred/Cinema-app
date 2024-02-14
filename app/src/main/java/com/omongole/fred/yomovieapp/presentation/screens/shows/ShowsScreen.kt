package com.omongole.fred.yomovieapp.presentation.screens.shows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.common.SearchWidget
import com.omongole.fred.yomovieapp.presentation.screens.shows.components.ShowsSection
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsScreenEvent
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsViewModel

@Composable
fun ShowsScreen(
    modifier: Modifier,
    searchShows: (String) -> Unit,
    showDetails: (Int) -> Unit
) {

    val showsViewModel: ShowsViewModel = hiltViewModel()
    val topRatedTvShows = showsViewModel.topRatedTvShows.collectAsLazyPagingItems()
    val popularTvShows = showsViewModel.popularTvShows.collectAsLazyPagingItems()
    val onAirTvShows = showsViewModel.onAirTvShows.collectAsLazyPagingItems()

    if ( (topRatedTvShows.loadState.refresh is LoadState.Error) &&
        (popularTvShows.loadState.refresh is LoadState.Error) &&
        (onAirTvShows.loadState.refresh is LoadState.Error)
    ) {

        NoInternetComponent(
            modifier = modifier.fillMaxSize(),
            error = "You're Offline!",
            refresh = {
                topRatedTvShows.refresh()
                popularTvShows.refresh()
                onAirTvShows.refresh()
            }
        )

    } else {

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState(), enabled = true)
        ) {
            SearchWidget(
                query = showsViewModel.searchQuery,
                placeHolder = "Search shows",
                onCloseClicked = {
                    showsViewModel.searchQuery = ""
                },
                onSearchClicked = {
                    if ( showsViewModel.searchQuery.isNotEmpty() ) {
                        searchShows( showsViewModel.searchQuery )
                    }
                },
                onValueChanged = {
                    showsViewModel.onEvent( ShowsScreenEvent.SearchQueryChange(it) )
                }
            )

            ShowsSection(
                shows = onAirTvShows,
                sectionTitle = "On Air",
                onShowClick = {
                    showDetails.invoke(it)
                }
            )

            ShowsSection(
                shows = topRatedTvShows,
                sectionTitle = "Top Rated",
                onShowClick = {
                    showDetails.invoke(it)
                }
            )

            ShowsSection(
                shows = popularTvShows,
                sectionTitle = "Popular",
                onShowClick = {
                    showDetails.invoke(it)
                }
            )
        }

    }

}