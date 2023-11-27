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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.presentation.components.AnimatedLargeImageShimmerEffect
import com.omongole.fred.yomovieapp.presentation.components.AnimatedTextShimmerEffect
import com.omongole.fred.yomovieapp.presentation.components.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.components.SearchWidget
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
                    AnimatedTextShimmerEffect()
                } else {
                    Text(
                        text = "Have You Watched?",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(10.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
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
                    LazyVerticalGrid(columns = GridCells.Fixed(2),contentPadding = PaddingValues(5.dp) ) {
                        items(
                            count = trendingMovies.itemCount,
                            //key = trendingMovies.itemKey { it.id },
                            contentType = trendingMovies.itemContentType{"contentType" }
                        ) {
                            val movie = trendingMovies[it]
                            movie?.let { m ->
                                AsyncImage(
                                    modifier = Modifier
                                        .padding(bottom = 7.dp, end = 4.dp)
                                        .height(350.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .clickable { showMovieDetail( m.id ) },
                                    model = "${Constants.BASE_IMAGE_URL}${m.posterPath}",
                                    contentDescription = "Poster Image",
                                    contentScale = ContentScale.FillBounds,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}