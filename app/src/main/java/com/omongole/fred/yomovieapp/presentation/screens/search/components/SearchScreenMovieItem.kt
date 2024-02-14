package com.omongole.fred.yomovieapp.presentation.screens.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.omongole.fred.yomovieapp.domain.model.movies.Movie
import com.omongole.fred.yomovieapp.presentation.common.PosterImage
import com.omongole.fred.yomovieapp.presentation.theme.FloralWhite
import com.omongole.fred.yomovieapp.presentation.theme.IndigoPurple
import com.omongole.fred.yomovieapp.presentation.theme.MidnightBlack
import com.omongole.fred.yomovieapp.presentation.theme.SeaGreen
import com.omongole.fred.yomovieapp.presentation.theme.TomatoRed
import com.omongole.fred.yomovieapp.presentation.theme.YoMovieAppTheme
import com.omongole.fred.yomovieapp.util.convertToPercentage
import com.omongole.fred.yomovieapp.util.displayPosterImage
import kotlinx.coroutines.Dispatchers

@Composable
fun SearchScreenMovieItem(
    showMovieDetail: (Int) -> Unit,
    movie: Movie
) {

    Column(
        modifier = Modifier.clickable { showMovieDetail(movie.id) }
    ) {

        Box(modifier = Modifier
            .padding(bottom = 7.dp, end = 15.dp)
            .clip(RoundedCornerShape(10.dp))
        ) {

            PosterImage(
                imageUrl = "${movie.posterPath}",
                width = null,
                cornerSize = 10.dp
            )

            ScoreBox(
                modifier = Modifier.padding(top = 333.dp, start = 15.dp),
                movie
            )
        }

        Text(
            text = movie.title,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 15.dp)
        )
        Text(
            text = movie.releaseDate,
            color = Color.Gray,
            modifier = Modifier.padding(start = 15.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
    }


}




@Composable
private fun ScoreBox(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .border(2.dp, TomatoRed, CircleShape)
            .background(SeaGreen)
            .padding(start = 7.dp),
    ) {

        if ( movie.rating != 0.toDouble() ) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(end = 10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = convertToPercentage(movie.rating),
                    fontSize = 11.sp,
                    color = FloralWhite,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = "%",
                    fontSize = 7.sp,
                    color = Color.LightGray,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 3.dp )
                )
            }
        } else {
            Text(
                text = "NR",
                fontSize = 14.sp,
                color = FloralWhite,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }

    }
}

@Preview( showBackground = true )
@Composable
fun ScorePreview() {
    YoMovieAppTheme {
        SearchScreenMovieItem(
            showMovieDetail = {},
            movie = Movie(
                id = 1,
                imagePath = null,
                title = "Kawundo",
                posterPath = null,
                releaseDate = "Jan 23, 2023",
                rating = 4.1984
            )
        )
    }
}