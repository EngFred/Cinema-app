package com.omongole.fred.yomovieapp.presentation.screens.genres

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import com.omongole.fred.yomovieapp.presentation.common.AnimatedSearchResultShimmerEffect
import com.omongole.fred.yomovieapp.presentation.common.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.common.ShowItem
import com.omongole.fred.yomovieapp.presentation.viewModel.GenreShowsResultScreenViewModel
import com.omongole.fred.yomovieapp.presentation.viewModel.GenreShowsResultScreenViewModelFactory
import com.omongole.fred.yomovieapp.presentation.viewModel.GenresShowsResultViewModelAssistedFactory

@Composable
fun GenreShowsResultScreen(
    modifier: Modifier,
    genreId: Long,
    assistedFactory: GenresShowsResultViewModelAssistedFactory,
    showDetail: (Int) -> Unit,
    showPoster: (String) -> Unit
) {

    val viewModel =  viewModel(
        modelClass = GenreShowsResultScreenViewModel::class.java,
        factory = GenreShowsResultScreenViewModelFactory(
            genreId, assistedFactory
        )
    )

    val movies = viewModel.shows.collectAsLazyPagingItems()

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
                    ShowItem(
                        onItemClick = showDetail,
                        onPosterClick = showPoster,
                        show = it
                    )
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
