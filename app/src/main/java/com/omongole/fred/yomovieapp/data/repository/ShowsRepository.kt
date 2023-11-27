package com.omongole.fred.yomovieapp.data.repository

import androidx.paging.PagingData
import com.omongole.fred.yomovieapp.data.modals.TvShow
import com.omongole.fred.yomovieapp.data.remote.ShowDetailResponse
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {
    fun fetchTopRatedTvShows() : Flow<PagingData<TvShow>>
    fun fetchPopularTvShows() : Flow<PagingData<TvShow>>
    fun fetchOnAirTvShows() : Flow<PagingData<TvShow>>
    fun searchShows( query: String ) : Flow<PagingData<TvShow>>
    fun fetchShowDetails( showId: Int ) : Flow<ShowDetailResponse>
}