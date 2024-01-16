package com.omongole.fred.yomovieapp.presentation.screens.home

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.presentation.components.AnimatedImageShimmerEffect
import com.omongole.fred.yomovieapp.presentation.components.AnimatedTextShimmerEffect
import com.omongole.fred.yomovieapp.presentation.components.Header
import com.omongole.fred.yomovieapp.presentation.components.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.theme.AlertDialogComponent
import com.omongole.fred.yomovieapp.presentation.viewModel.HomeScreenEvent
import com.omongole.fred.yomovieapp.presentation.viewModel.HomeScreenViewModel
import com.omongole.fred.yomovieapp.util.Constants.BASE_IMAGE_URL
import com.omongole.fred.yomovieapp.util.Constants.BASE_IMAGE_URL_W500

@Composable
fun HomeScreen(
    modifier: Modifier,
    showMovieDetail: (Int) -> Unit,
    darkTheme: Boolean
) {
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()

    val upComingMovies = homeScreenViewModel.upComingMovies.collectAsLazyPagingItems()
    val nowPlayingMovies = homeScreenViewModel.nowPlayingMovies.collectAsLazyPagingItems()
    val popularMovies = homeScreenViewModel.popularMovies.collectAsLazyPagingItems()
    val topRatedMovies = homeScreenViewModel.topRatedMovies.collectAsLazyPagingItems()

    if ( (nowPlayingMovies.loadState.refresh is LoadState.Error) &&
        (upComingMovies.loadState.refresh is LoadState.Error) &&
        (topRatedMovies.loadState.refresh is LoadState.Error) &&
        (popularMovies.loadState.refresh is LoadState.Error )) {

        val error = (nowPlayingMovies.loadState.refresh as LoadState.Error).error

        NoInternetComponent(
            modifier = modifier.fillMaxSize(),
            error = error.localizedMessage!!,
            refresh = {
                nowPlayingMovies.refresh()
                upComingMovies.refresh()
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

            var dialogState by remember { mutableStateOf(false)  }
            AlertDialogComponent(
                dialogState = dialogState,
                onOkClicked = { dialogState = false },
                onDismiss = { dialogState = false }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Header(onClick = {
                homeScreenViewModel.onEvent( HomeScreenEvent.ThemeToggled(it) )
            }, themeMode = darkTheme, infoIconClick = {
                dialogState = true
            } )
            if ( nowPlayingMovies.loadState.refresh == LoadState.Loading ) {
                AnimatedTextShimmerEffect()
            } else {
                Text(
                    text = "Now Playing",
                    modifier = Modifier.padding( start = 10.dp, bottom = 10.dp),
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
                    count = nowPlayingMovies.itemCount,
                    //key = nowPlayingMovies.itemKey { it.id },
                    contentType = nowPlayingMovies.itemContentType{"contentType" }
                ) {
                    nowPlayingMovies[it]?.let {
                        AsyncImage(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .height(200.dp)
                                .width(145.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showMovieDetail(it.id) },
                            model = "${BASE_IMAGE_URL_W500}${it.posterPath}",
                            contentDescription = "Poster Image",
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }
                val loadState = nowPlayingMovies.loadState
                item {
                    if ( loadState.refresh == LoadState.Loading ) {
                        Row {
                            repeat(10){
                                AnimatedImageShimmerEffect()
                            }
                        }
                    }
                }
            }
            if ( upComingMovies.loadState.refresh == LoadState.Loading ) {
                AnimatedTextShimmerEffect()
            } else {
                Text(
                    text = "UpComing Movies",
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
                    count = upComingMovies.itemCount,
                    //key = upComingMovies.itemKey { it.id },
                    contentType = upComingMovies.itemContentType{"contentType" }
                ) {
                    upComingMovies[it]?.let {
                        AsyncImage(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .height(200.dp)
                                .width(145.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showMovieDetail(it.id) },
                            model = "${BASE_IMAGE_URL_W500}${it.posterPath}",
                            contentDescription = "Poster Image",
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }
                val loadState = upComingMovies.loadState
                item {
                    if ( loadState.refresh == LoadState.Loading ) {
                        Row {
                            repeat(10){
                                AnimatedImageShimmerEffect()
                            }
                        }
                    }
                }
            }
            if ( topRatedMovies.loadState.refresh == LoadState.Loading ) {
                AnimatedTextShimmerEffect()
            } else {
                Text(
                    text = "Top Rated Movies",
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
                    count = topRatedMovies.itemCount,
                    //key = topRatedMovies.itemKey { it.id },
                    contentType = topRatedMovies.itemContentType{"contentType" }
                ) {
                    topRatedMovies[it]?.let {
                        AsyncImage(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .height(200.dp)
                                .width(145.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showMovieDetail(it.id) },
                            model = "${BASE_IMAGE_URL_W500}${it.posterPath}",
                            contentDescription = "Poster Image",
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }
                val loadState = topRatedMovies.loadState
                item {
                    if ( loadState.refresh == LoadState.Loading ) {
                        Row {
                            repeat(10){
                                AnimatedImageShimmerEffect()
                            }
                        }
                    }
                }
            }
            if ( popularMovies.loadState.refresh == LoadState.Loading ) {
                AnimatedTextShimmerEffect()
            } else {
                Text(
                    text = "Popular Movies",
                    modifier = Modifier.padding(10.dp),
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.size(12.dp))
            LazyRow(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
            ) {
                items(
                    count = popularMovies.itemCount,
                    //key = popularMovies.itemKey { it.id },
                    contentType = popularMovies.itemContentType{"contentType" }
                ) {
                    popularMovies[it]?.let {
                        AsyncImage(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .height(200.dp)
                                .width(145.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showMovieDetail(it.id) },
                            model = "${BASE_IMAGE_URL_W500}${it.posterPath}",
                            contentDescription = "Poster Image",
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }
                val loadState = popularMovies.loadState
                item {
                    if ( loadState.refresh == LoadState.Loading ) {
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