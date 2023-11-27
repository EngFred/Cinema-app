package com.omongole.fred.yomovieapp.presentation.screens.detail
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.presentation.components.AnimatedDetailShimmerEffect
import com.omongole.fred.yomovieapp.presentation.components.NoInternetComponent
import com.omongole.fred.yomovieapp.util.Constants
import com.omongole.fred.yomovieapp.util.Resource

@Composable
fun ShowDetailScreen(
    showId: Int,
    modifier: Modifier,
    assistedFactory: ShowDetailScreenViewModelAssistedFactory,
    showPoster: (String) -> Unit
) {

    val viewModel =  viewModel(
        modelClass = ShowDetailScreenViewModel::class.java,
        factory = ShowDetailScreenViewModelFactory(
            showId, assistedFactory
        )
    )

    val showDetailState = viewModel.show.collectAsState().value

    when( showDetailState ) {

        is Resource.Loading -> {
            AnimatedDetailShimmerEffect()
        }
        is Resource.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NoInternetComponent(modifier = modifier, error = showDetailState.message, refresh = {
                    viewModel.getShowDetail(showId)
                } )
            }
        }
        is Resource.Success -> {
            val show = showDetailState.result
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Text(
                    text = show.name,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Row( modifier = Modifier.fillMaxWidth() ) {
                    AsyncImage(
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .weight(1f)
                            .height(380.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                show.posterPath?.let { posterPath ->
                                    showPoster(posterPath)
                                }
                            },
                        model = "${Constants.BASE_IMAGE_URL}${show.posterPath}",
                        contentDescription = "Poster Image",
                        contentScale = ContentScale.FillBounds,
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .height(380.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(text = "Production Companies:", fontWeight = FontWeight.Bold)
                        if (  show.productionCompanies.isEmpty() ) {
                            Text(text = "Non listed", color = Color.Red)
                        }else {
                            Text(text = show.productionCompanies.joinToString { it.name })
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                        Text(text = "Production Countries:", fontWeight = FontWeight.Bold)
                        if ( show.productionCountries.isEmpty() ) {
                            Text(text = "Non listed", color = Color.Red)
                        } else {
                            Text(text = show.productionCountries.joinToString { it.name })
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                if ( show.tagline != "" ) {
                    Text(
                        text = show.tagline ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }
                Text(text = "Aired from ${show.firstAirDate} to ${show.lastAirDate}", modifier = Modifier.padding( start = 10.dp ))
                Spacer(modifier = Modifier.size(10.dp))
                Divider()
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    val seasonNoCheck = if ( show.numberOfSeasons > 1 ) "seasons" else "season"
                    val episodeNoCheck = if ( show.numberOfEpisodes > 1 ) "episodes" else "episode"
                    Text(text = "${ show.numberOfSeasons } $seasonNoCheck", modifier = Modifier.padding( end = 10.dp ))
                    Spacer(
                        modifier = Modifier
                            .height(24.dp)
                            .width(2.dp)
                            .background(color = Color.Gray)
                            .padding(10.dp)
                    )
                    Text(text = "${show.numberOfEpisodes} $episodeNoCheck", modifier = Modifier.padding( start = 10.dp, end = 10.dp))
                    Spacer(
                        modifier = Modifier
                            .height(24.dp)
                            .width(2.dp)
                            .background(color = Color.Gray)
                            .padding(10.dp)
                    )
                    val rating = String.format("%.1f", show.rating)
                    Text(text = "$rating rating", modifier = Modifier.padding( start = 10.dp ))
                }
                Spacer(modifier = Modifier.size(10.dp))
                Divider()
                Spacer(modifier = Modifier.size(10.dp))
                if ( show.networks.isNotEmpty() ) {
                    Text(text = "Networks:", fontWeight = FontWeight.Medium, modifier = Modifier.padding( start = 10.dp ))
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        modifier = Modifier
                            .wrapContentWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(start = 10.dp)
                    ) {
                        show.networks.forEach {
                            AsyncImage(
                                modifier = Modifier
                                    .padding(horizontal = 6.dp)
                                    .height(75.dp)
                                    .width(100.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        it.logoPath?.let { posterPath ->
                                            showPoster(posterPath)
                                        }
                                    },
                                model = "${Constants.BASE_IMAGE_URL}${it.logoPath}",
                                contentDescription = "Poster Image",
                                contentScale = ContentScale.Fit,
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(text = "Genres: ", fontWeight = FontWeight.Bold)
                    if ( show.genres.isNotEmpty() ) Text(text = show.genres.joinToString { it.name })
                    else Text(text = "Not listed", color = Color.Red)
                }
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Spoken Languages: ", fontWeight = FontWeight.Bold)
                    if ( show.spokenLanguages.isEmpty() ) Text(text = "Not listed", color = Color.Red)
                    else Text(text = show.spokenLanguages.joinToString { it.name })
                }
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Status: ", fontWeight = FontWeight.Bold)
                    Text(text = show.status ?: "", color = Color.Red)
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = show.overview, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp))
            }
        }
    }
}