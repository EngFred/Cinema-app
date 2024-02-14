package com.omongole.fred.yomovieapp.presentation.screens.shows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.omongole.fred.yomovieapp.presentation.common.AnimatedSearchResultShimmerEffect
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.common.ShowItem
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsSearchResultScreenViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsSearchResultScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.ShowsSearchResultScreenViewModelFactory

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
            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp) ) {
                Text(text = "Search results for: ")
                Text(
                    text = query.trimEnd(),
                    Modifier.padding(start = 4.dp),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            LazyColumn{
                items(
                    count = shows.itemCount,
                    //key = shows.itemKey { it.id },
                    contentType = shows.itemContentType{"shows"}
                ) {
                    shows[it]?.let {
                        ShowItem(
                            onItemClick = showDetails,
                            onPosterClick = showPoster,
                            show = it
                        )
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