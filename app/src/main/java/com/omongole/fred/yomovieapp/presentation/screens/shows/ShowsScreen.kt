package com.omongole.fred.yomovieapp.presentation.screens.shows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.omongole.fred.yomovieapp.presentation.components.AnimatedImageShimmerEffect
import com.omongole.fred.yomovieapp.presentation.components.AnimatedTextShimmerEffect
import com.omongole.fred.yomovieapp.presentation.components.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.components.SearchWidget
import com.omongole.fred.yomovieapp.util.Constants


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
        (onAirTvShows.loadState.refresh is LoadState.Error)) {

        val error = (topRatedTvShows.loadState.refresh as LoadState.Error).error
        NoInternetComponent(
            modifier = modifier.fillMaxSize(),
            error = error.localizedMessage!!,
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
                placeHolder = "Search shows & series",
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
            if ( onAirTvShows.loadState.refresh == LoadState.Loading ) {
                AnimatedTextShimmerEffect()
            }else {
                Text(
                    text = "On Air",
                    modifier = Modifier.padding(10.dp),
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            LazyRow(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) {
                items(
                    count = onAirTvShows.itemCount,
                    //key = onAirTvShows.itemKey { it.id },
                    contentType = onAirTvShows.itemContentType{"contentType" }
                ) {
                    onAirTvShows[it]?.let {
                        AsyncImage(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .height(240.dp)
                                .width(155.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    showDetails(it.id)
                                },
                            model = "${Constants.BASE_IMAGE_URL}${it.posterPath}",
                            contentDescription = "Poster Image",
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }
                item {
                    if ( onAirTvShows.loadState.refresh == LoadState.Loading ) {
                        Row {
                            repeat(10){
                                AnimatedImageShimmerEffect()
                            }
                        }
                    }
                }
            }
            if ( topRatedTvShows.loadState.refresh == LoadState.Loading ) {
                AnimatedTextShimmerEffect()
            }else {
                Text(
                    text = "Top Rated",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            LazyRow(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) {
                items(
                    count = topRatedTvShows.itemCount,
                    //key = topRatedTvShows.itemKey { it.id },
                    contentType = topRatedTvShows.itemContentType{"contentType" }
                ) {
                    topRatedTvShows[it]?.let {
                        AsyncImage(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .height(240.dp)
                                .width(155.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    showDetails(it.id)
                                },
                            model = "${Constants.BASE_IMAGE_URL}${it.posterPath}",
                            contentDescription = "Poster Image",
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }
                item {
                    if ( topRatedTvShows.loadState.refresh == LoadState.Loading ) {
                        Row {
                            repeat(10){
                                AnimatedImageShimmerEffect()
                            }
                        }
                    }
                }
            }
            if ( popularTvShows.loadState.refresh == LoadState.Loading ) {
                AnimatedTextShimmerEffect()
            }else {
                Text(
                    text = "Popular",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            LazyRow(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) {
                items(
                    count = popularTvShows.itemCount,
                    //key = popularTvShows.itemKey { it.id },
                    contentType = popularTvShows.itemContentType{"contentType" }
                ) {
                    popularTvShows[it]?.let {
                        AsyncImage(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .height(240.dp)
                                .width(155.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    showDetails(it.id)
                                },
                            model = "${Constants.BASE_IMAGE_URL}${it.posterPath}",
                            contentDescription = "Poster Image",
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }
                item {
                    if ( popularTvShows.loadState.refresh == LoadState.Loading ) {
                        Row {
                            repeat(10){
                                AnimatedImageShimmerEffect()
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(15.dp))
        }

    }

}