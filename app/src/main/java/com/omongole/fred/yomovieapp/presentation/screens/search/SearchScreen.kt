package com.omongole.fred.yomovieapp.presentation.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.presentation.common.AnimatedLargeImageShimmerEffect
import com.omongole.fred.yomovieapp.presentation.common.AnimatedTextShimmerEffect
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.common.SearchWidget
import com.omongole.fred.yomovieapp.presentation.screens.search.components.SearchScreenMovieItem
import com.omongole.fred.yomovieapp.presentation.viewModel.SearchScreenEvent
import com.omongole.fred.yomovieapp.presentation.viewModel.SearchScreenViewModel
import com.omongole.fred.yomovieapp.util.Constants

@Composable
fun SearchScreen(
    modifier: Modifier,
    searchMovies: (String) -> Unit,
    showMovieDetail: (Int) -> Unit
) {

    val viewModel: SearchScreenViewModel = hiltViewModel()
    val trendingMovies = viewModel.trendingMovies.collectAsLazyPagingItems()

    when( trendingMovies.loadState.refresh ) {
        is LoadState.Error -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NoInternetComponent(modifier = modifier, error = "You're offline!", refresh = { trendingMovies.refresh() } )
            }
        }
        else -> {
            Column(
                modifier = modifier
            ) {
                SearchWidget(
                    query = viewModel.searchQuery,
                    placeHolder = "Search movies",
                    onCloseClicked = {
                        viewModel.searchQuery = ""
                    },
                    onSearchClicked = {
                        if ( viewModel.searchQuery.isNotEmpty() ) {
                            searchMovies( viewModel.searchQuery )
                        }
                    },
                    onValueChanged = {
                        viewModel.onEvent( SearchScreenEvent.SearchQueryChange(it) )
                    }
                )
                if ( trendingMovies.loadState.refresh == LoadState.Loading ) {
                    Column( modifier = Modifier
                        .wrapContentHeight()
                        .padding(5.dp)) {
                        Row {
                            AnimatedLargeImageShimmerEffect()
                            Spacer(modifier = Modifier.width(7.dp))
                            AnimatedLargeImageShimmerEffect()
                        }
                        Spacer(modifier = Modifier.size(7.dp))
                        Row {
                            AnimatedLargeImageShimmerEffect()
                            Spacer(modifier = Modifier.width(7.dp))
                            AnimatedLargeImageShimmerEffect()
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(start = 15.dp)
                    ) {
                        items(
                            count = trendingMovies.itemCount,
                            //key = trendingMovies.itemKey { it.id },
                            contentType = trendingMovies.itemContentType{"trendingMovies" }
                        ) {
                            val movie = trendingMovies[it]
                            movie?.let {
                                SearchScreenMovieItem(
                                    showMovieDetail = showMovieDetail,
                                    movie = it
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}