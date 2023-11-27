package com.omongole.fred.yomovieapp.presentation.screens.genres

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.presentation.components.AnimatedSearchResultShimmerEffect
import com.omongole.fred.yomovieapp.presentation.components.NoInternetComponent
import com.omongole.fred.yomovieapp.util.Constants

@Composable
fun GenreResultScreen(
    modifier: Modifier,
    genreId: Long,
    assistedFactory: GenreResultViewModelAssistedFactory,
    showMovieDetail: (Int) -> Unit,
    showMoviePoster: (String) -> Unit
) {

    val viewModel =  viewModel(
        modelClass = GenreResultScreenViewModel::class.java,
        factory = GenreResultScreenViewModelFactory(
            genreId, assistedFactory
        )
    )

    val movies = viewModel.movies.collectAsLazyPagingItems()

    if ( movies.loadState.refresh is LoadState.Error ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoInternetComponent(modifier = modifier, error = "You're offline!", refresh = {
                movies.refresh()
            })
        }
    } else {

        LazyColumn{
            items(
                count = movies.itemCount,
                //key = movies.itemKey { it.id },
                contentType = movies.itemContentType{"contentType" }
            ) {
                movies[it]?.let {
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth().clickable { showMovieDetail(it.id) }
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .padding(horizontal = 6.dp)
                                .height(150.dp)
                                .width(120.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    it.posterPath?.let {posterPath ->
                                        showMoviePoster(posterPath)
                                    }
                                },
                            model = "${Constants.BASE_IMAGE_URL}${it.posterPath}",
                            contentDescription = "Poster Image",
                            contentScale = ContentScale.FillBounds,
                        )
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = it.title,
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.size(15.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = it.releaseDate, modifier = Modifier.padding( end = 10.dp ))
                                Spacer(
                                    modifier = Modifier
                                        .height(24.dp)
                                        .width(2.dp)
                                        .background(color = Color.Gray)
                                )
                                val rating = String.format("%.1f", it.rating)
                                Text(text = "$rating rating", modifier = Modifier.padding( start = 10.dp ))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Divider()
                }
            }
            if ( movies.loadState.refresh == LoadState.Loading ) {
                item {
                    repeat(10) {
                        AnimatedSearchResultShimmerEffect()
                    }
                }
            }
        }

    }
}
