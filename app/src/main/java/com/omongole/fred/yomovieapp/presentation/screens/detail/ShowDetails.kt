package com.omongole.fred.yomovieapp.presentation.screens.detail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.domain.model.shows.ShowDetail
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Company
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Country
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Genre
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Language
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Network
import com.omongole.fred.yomovieapp.domain.model.valueObjects.ShowCreator
import com.omongole.fred.yomovieapp.presentation.theme.CoralOrange
import com.omongole.fred.yomovieapp.presentation.theme.CoralRed
import com.omongole.fred.yomovieapp.presentation.theme.DodgerBlue
import com.omongole.fred.yomovieapp.presentation.theme.FireEngineRed
import com.omongole.fred.yomovieapp.presentation.theme.MidnightBlack
import com.omongole.fred.yomovieapp.presentation.theme.PumpkinOrange
import com.omongole.fred.yomovieapp.presentation.theme.SeaGreen
import com.omongole.fred.yomovieapp.presentation.theme.SteelBlue
import com.omongole.fred.yomovieapp.presentation.theme.WhiteSmoke
import com.omongole.fred.yomovieapp.presentation.theme.YoMovieAppTheme
import com.omongole.fred.yomovieapp.util.Constants
import com.omongole.fred.yomovieapp.util.displayOriginalImage
import com.omongole.fred.yomovieapp.util.displayPosterImage
import com.omongole.fred.yomovieapp.util.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDetails(
    show: ShowDetail,
    showPoster: (String) -> Unit
) {

    Column( 
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AsyncImage(
                model = displayOriginalImage( show.backdropPath ),
                contentDescription = "backdrop_image",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(MidnightBlack),
                contentScale = ContentScale.Crop
            )
            Row (
                modifier = Modifier
                    .padding(top = 173.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
                    .height(320.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ){
                Card(
                    onClick = {
                        show.posterPath?.let { posterPath ->
                            showPoster(posterPath)
                        }
                    },
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .weight(1f)
                        .height(320.dp)
                        .clip(RoundedCornerShape(8.dp)),
                ) {
                    AsyncImage(
                        modifier = Modifier.background(Color.DarkGray),
                        model = displayPosterImage(show.posterPath),
                        contentDescription = "Poster Image",
                        contentScale = ContentScale.Crop,
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(top = 70.dp)
                        .weight(1f)
                        .height(380.dp)
                ) {
                    Text(
                        text = show.name,
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = 3
                    )
                    if ( show.rating != 0.toDouble() ) {
                        Spacer(modifier = Modifier.size(11.dp))
                        val rating = String.format("%.1f", show.rating)
                        Text( text = "$rating rating" )
                    } else {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text( text = "-", modifier = Modifier.padding(start = 7.dp))
                    }
                    if ( !show.status.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${show.status}",
                            fontFamily = FontFamily.Cursive,
                            color = if ( show.status == "Ended" ) FireEngineRed else PumpkinOrange
                        )
                    } else {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text( text = "-", modifier = Modifier.padding(start = 7.dp))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column {
                        Row {
                            Text(
                                text = "From:",
                                modifier = Modifier.padding(end = 4.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            if ( !show.firstAirDate.isNullOrEmpty() ) {
                                Text(
                                    text = formatDate(show.firstAirDate),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.Gray
                                )
                            }else {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text( text = "-", modifier = Modifier.padding(start = 7.dp))
                            }
                        }
                        Row {
                            Text(
                                text = "To:",
                                modifier = Modifier.padding(end = 4.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            if ( !show.lastAirDate.isNullOrEmpty() ) {
                                Text(
                                    text = formatDate(show.lastAirDate),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = if ( show.status == "Ended" ) FireEngineRed else PumpkinOrange
                                )
                            }else {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text( text = "-", modifier = Modifier.padding(start = 7.dp))
                            }
                        }
                    }
                }
            }
        }

        if ( show.tagline != "" ) {
            Text(
                text = show.tagline ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                fontSize = 17.sp
            )
        }

        if ( !show.overview.isNullOrEmpty() ) {
            Text(
                text = "Overview",
                Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = SeaGreen
            )
            Text(
                text = show.overview,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Left
            )
        }

        Divider()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            val seasonNoCheck = if ( show.numberOfSeasons > 1 ) "seasons" else "season"
            val episodeNoCheck = if ( show.numberOfEpisodes > 1 ) "episodes" else "episode"
            Text(text = "${ show.numberOfSeasons } $seasonNoCheck", modifier = Modifier.padding( end = 10.dp ), fontWeight = FontWeight.Bold)
            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .width(2.dp)
                    .background(color = Color.Gray)
                    .padding(10.dp)
            )
            Text(text = "${show.numberOfEpisodes} $episodeNoCheck", modifier = Modifier.padding( start = 10.dp, end = 10.dp), fontWeight = FontWeight.Bold)
        }
        Divider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 10.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(text = "Genres: ", fontWeight = FontWeight.Bold, color = SeaGreen)
            Text(text = show.genres.joinToString { it.name })
        }

        Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            if ( show.productionCompanies.isNotEmpty() ) {
                Text(
                    text = "Production Companies:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = SeaGreen
                )
                Text(text = show.productionCompanies.joinToString { it.name })
                Spacer(modifier = Modifier.size(12.dp))
            }
            if ( show.productionCountries.isNotEmpty() ) {
                Text(
                    text = "Production Countries:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp),
                    color = SeaGreen
                )
                Text(text = show.productionCountries.joinToString { it.name })
            }
        }

        if ( show.networks.isNotEmpty() ) {
            Text(
                text = "Networks:",
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                color = SeaGreen
            )
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 10.dp)
            ) {
                show.networks.forEach {
                    Box(
                        modifier = Modifier
                            .height(75.dp)
                            .width(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                it.logoPath?.let { posterPath ->
                                    showPoster(posterPath)
                                }
                            }.background(WhiteSmoke)
                            .padding(horizontal = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = displayPosterImage(it.logoPath),
                            contentDescription = "Poster Image",
                            contentScale = ContentScale.Fit,
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
        Spacer(modifier = Modifier.size(10.dp))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview( showBackground = true )
@Composable
fun ShowDetailsPreview() {

    YoMovieAppTheme {

        ShowDetails(
            showPoster = {},
            show = ShowDetail(
                id = 1,
                createdBy = listOf(
                    ShowCreator(id=1, name= "Douglas", null)
                ),
                firstAirDate = "Jan, 03, 2001",
                lastAirDate = "Jan, 06, 2005",
                genres = listOf(
                    Genre(id = 1, name = "Science"),
                    Genre(id = 2, name = "Action"),
                    Genre(id = 3, name = "Mystery"),
                ),
                spokenLanguages = listOf(
                    Language("Japanese")
                ),
                name = "Teng Teng",
                networks = listOf(
                    Network(id=1, null, "ABS-CBN")
                ),
                numberOfEpisodes = 100,
                numberOfSeasons = 23,
                productionCountries = listOf(
                    Country("Japan"),
                    Country("Singapore")
                ),
                productionCompanies = listOf(
                    Company( id = 1, logoPath = null, name = "Netflix" )
                ),
                posterPath = null,
                backdropPath = null,
                overview = "After a deadly earthquake turns Seoul into a lawless badland, a fearless huntsman springs into action to rescue a teenager abducted by a mad doctor.",
                status = "Returning series",
                tagline = "Hehe tujja tujja!",
                rating = 4.2
            )
        )
    }
}