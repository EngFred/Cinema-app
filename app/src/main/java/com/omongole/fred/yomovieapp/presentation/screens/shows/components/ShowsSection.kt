package com.omongole.fred.yomovieapp.presentation.screens.shows.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.domain.model.shows.Show
import com.omongole.fred.yomovieapp.presentation.common.AnimatedImageShimmerEffect
import com.omongole.fred.yomovieapp.presentation.common.PosterImage
import com.omongole.fred.yomovieapp.util.Constants

@Composable
fun ShowsSection(
    modifier: Modifier = Modifier,
    shows : LazyPagingItems<Show>,
    sectionTitle: String,
    onShowClick: (Int) -> Unit
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        //header
        Text(
            text = sectionTitle,
            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )

        //list
        LazyRow(
            modifier = Modifier
                .height(240.dp)
                .wrapContentWidth()
                .padding(bottom = 10.dp)
        ) {

            when ( shows.loadState.refresh ) {
                is LoadState.Error -> {
                    item {
                        Row {
                            repeat(10) {
                                AnimatedImageShimmerEffect()
                            }
                        }
                    }
                }
                LoadState.Loading -> {
                    item {
                        Row {
                            repeat(10) {
                                AnimatedImageShimmerEffect()
                            }
                        }
                    }
                }
                is LoadState.NotLoading -> {

                    items(
                        count = shows.itemCount,
                        //key = nowPlayingMovies.itemKey { it.id },
                        contentType = shows.itemContentType { "shows" }
                    ) {
                        shows[it]?.let {
                            PosterImage(
                                imageUrl = "${it.posterPath}",
                                width = 155.dp,
                                height = 240.dp,
                                scaleType = ContentScale.FillBounds,
                                id = it.id,
                                onClick = onShowClick
                            )
                        }
                    }
                }
            }
        }
    }
}