package com.omongole.fred.yomovieapp.domain.repository

import androidx.paging.PagingData
import com.omongole.fred.yomovieapp.domain.modals.valueObjects.Genre
import com.omongole.fred.yomovieapp.domain.modals.TvShow
import com.omongole.fred.yomovieapp.domain.modals.ShowDetailResponse
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {
    fun fetchTopRatedTvShows() : Flow<PagingData<TvShow>>
    fun fetchPopularTvShows() : Flow<PagingData<TvShow>>
    fun fetchOnAirTvShows() : Flow<PagingData<TvShow>>
    fun searchShows( query: String ) : Flow<PagingData<TvShow>>
    fun fetchShowDetails( showId: Int ) : Flow<ShowDetailResponse>
    fun getShowsGenres() :  Flow<List<Genre>>
    fun fetchShowsByGenre( genreId: Long ) : Flow<PagingData<TvShow>>
}