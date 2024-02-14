package com.omongole.fred.yomovieapp.domain.repository

import androidx.paging.PagingData
import com.omongole.fred.yomovieapp.data.model.valueObjects.GenreDTO
import com.omongole.fred.yomovieapp.data.model.shows.ShowDTO
import com.omongole.fred.yomovieapp.data.model.shows.ShowDetailDTO
import com.omongole.fred.yomovieapp.domain.model.shows.Show
import com.omongole.fred.yomovieapp.domain.model.shows.ShowDetail
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Genre
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {
    fun fetchTopRatedTvShows() : Flow<PagingData<Show>>
    fun fetchPopularTvShows() : Flow<PagingData<Show>>
    fun fetchOnAirTvShows() : Flow<PagingData<Show>>
    fun searchShows( query: String ) : Flow<PagingData<Show>>
    fun fetchShowDetails( showId: Int ) : Flow<ShowDetail>
    fun getShowsGenres() :  Flow<List<Genre>>
    fun fetchShowsByGenre( genreId: Long ) : Flow<PagingData<Show>>
}