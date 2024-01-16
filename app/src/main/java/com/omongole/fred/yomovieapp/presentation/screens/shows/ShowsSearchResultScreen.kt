package com.omongole.fred.yomovieapp.presentation.screens.shows

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
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.presentation.components.AnimatedSearchResultShimmerEffect
import com.omongole.fred.yomovieapp.presentation.components.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsSearchResultScreenViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsSearchResultScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsSearchResultScreenViewModelFactory
import com.omongole.fred.yomovieapp.util.Constants
import com.omongole.fred.yomovieapp.util.Constants.BASE_IMAGE_URL_W500

@Composable
fun ShowsSearchResultScreen(
    query: String,
    assistedFactory: ShowsSearchResultScreenViewModelAssistedFactory,
    modifier: Modifier,
    showDetails: (Int) -> Unit,
    showPoster: (String) -> Unit
) {

    val viewModel =  viewModel(
        modelClass = ShowsSearchResultScreenViewModel::class.java,
        factory = ShowsSearchResultScreenViewModelFactory(
            query, assistedFactory
        )
    )

    val shows = viewModel.shows.collectAsLazyPagingItems()


    if ( shows.loadState.refresh is LoadState.Error ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoInternetComponent(modifier = modifier, error = "You're offline!", refresh = {
                shows.refresh()
            })
        }
    } else {
        Column{
            Text(text = "Search results for '${query}'", Modifier.padding(start = 10.dp))
            Spacer(modifier = Modifier.size(8.dp))
            LazyColumn{
                items(
                    count = shows.itemCount,
                    //key = shows.itemKey { it.id },
                    contentType = shows.itemContentType{"contentType" }
                ) {
                    shows[it]?.let {
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showDetails(it.id)
                                }
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp, vertical = 3.dp)
                                    .height(150.dp)
                                    .width(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        showPoster(it.posterPath)
                                    },
                                model = "${BASE_IMAGE_URL_W500}${it.posterPath}",
                                contentDescription = "Poster Image",
                                contentScale = ContentScale.FillBounds,
                            )
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 18.dp, horizontal = 8.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = it.name,
                                    fontWeight = FontWeight.Medium,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.size(15.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = it.firstAirDate, modifier = Modifier.padding( end = 10.dp ))
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
                        Divider()
                    }
                }
                if ( shows.loadState.refresh == LoadState.Loading ) {
                    item {
                        repeat(10) {
                            AnimatedSearchResultShimmerEffect()
                        }
                    }
                }
            }
        }
    }
}