package com.omongole.fred.yomovieapp.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.omongole.fred.yomovieapp.domain.model.shows.Show
import com.omongole.fred.yomovieapp.util.Constants
import com.omongole.fred.yomovieapp.util.displayPosterImage

@Composable
fun ShowItem(
    onItemClick: (Int) -> Unit,
    onPosterClick: (String) -> Unit,
    show: Show
) {

    val shouldShowShow = show.rating != 0.toDouble() && !show.firstAirDate.isNullOrEmpty()

    if ( shouldShowShow ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onItemClick.invoke(show.id) }
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(end = 6.dp)
                    .height(150.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onPosterClick(show.posterPath)
                    }.background(Color.LightGray),
                model = displayPosterImage(show.posterPath),
                contentDescription = "Poster Image",
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = Modifier.wrapContentHeight()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = show.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(15.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = show.firstAirDate, modifier = Modifier.padding( end = 10.dp ))
                    Spacer(
                        modifier = Modifier
                            .height(24.dp)
                            .width(2.dp)
                            .background(color = Color.Gray)
                    )
                    val rating = String.format("%.1f",show.rating)
                    Text(text = "$rating rating", modifier = Modifier.padding( start = 10.dp ))
                }
            }
        }
    }

}