package com.omongole.fred.yomovieapp.presentation.screens.detail
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.presentation.components.AnimatedDetailShimmerEffect
import com.omongole.fred.yomovieapp.presentation.components.NoInternetComponent
import com.omongole.fred.yomovieapp.presentation.theme.YoMovieAppTheme
import com.omongole.fred.yomovieapp.util.Constants
import com.omongole.fred.yomovieapp.util.Resource

@Composable
fun MovieDetailScreen(
    movieId: Int,
    modifier: Modifier,
    assistedFactory: MovieDetailScreenViewModelAssistedFactory,
    showMoviePoster: (String) -> Unit
) {

    val viewModel =  viewModel(
        modelClass = MovieDetailScreenViewModel::class.java,
        factory = MovieDetailScreenViewModelFactory(
            movieId, assistedFactory
        )
    )

    val movieDetailState = viewModel.movie.collectAsState().value

    when( movieDetailState ) {

        is Resource.Loading -> {
            AnimatedDetailShimmerEffect()
        }
        is Resource.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NoInternetComponent(modifier = modifier, error = movieDetailState.message, refresh = {
                    viewModel.getMovieDetail()
                } )
            }
        }
        is Resource.Success -> {
            val movie = movieDetailState.result
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Text(
                    text = movie.title,
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
                                movie.posterPath?.let { posterPath ->
                                    showMoviePoster(posterPath)
                                }
                            },
                        model = "${Constants.BASE_IMAGE_URL}${movie.posterPath}",
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
                        if (  movie.productionCompanies.isEmpty() ) {
                            Text(text = "Non listed", color = Color.Red)
                        }else {
                            Text(text = movie.productionCompanies.joinToString { it.name })
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                        Text(text = "Production Countries:", fontWeight = FontWeight.Bold)
                        if ( movie.productionCountries.isEmpty() ) {
                            Text(text = "Non listed", color = Color.Red)
                        } else {
                            Text(text = movie.productionCountries.joinToString { it.name })
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                if ( movie.tagline != "" ) {
                    Text(
                        text = movie.tagline ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }
                Divider()
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(text = movie.releaseDate ?: "", modifier = Modifier.padding( end = 10.dp ))
                    Spacer(
                        modifier = Modifier
                            .height(24.dp)
                            .width(2.dp)
                            .background(color = Color.Gray)
                            .padding(10.dp)
                    )
                    val rating = String.format("%.1f", movie.rating)
                    Text(text = "$rating rating", modifier = Modifier.padding( start = 10.dp, end = 10.dp ))
                    Spacer(
                        modifier = Modifier
                            .height(24.dp)
                            .width(2.dp)
                            .background(color = Color.Gray)
                            .padding(10.dp)
                    )
                    Text(text = "${movie.duration} mins", modifier = Modifier.padding( start = 10.dp ))
                }
                Spacer(modifier = Modifier.size(10.dp))
                Divider()
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(text = "Genres: ", fontWeight = FontWeight.Bold)
                    Text(text = movie.genres.joinToString { it.name })
                }
                Spacer(modifier = Modifier.size(10.dp))
                if ( movie.budget != 0L ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Budget: ", fontWeight = FontWeight.Bold)
                        Text(text = "$${movie.budget}", color = Color.Red)
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                }
                Text(text = movie.overView ?: "", modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp))
            }
        }
    }
}

@Preview
@Composable
fun OnePrev() {
    YoMovieAppTheme {

    }
}