package com.omongole.fred.yomovieapp.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.omongole.fred.yomovieapp.data.modals.TvShow
import com.omongole.fred.yomovieapp.data.remote.MovieApi
import com.omongole.fred.yomovieapp.data.remote.ShowDetailResponse
import com.omongole.fred.yomovieapp.data.remote.source.shows.OnAirTvShowsPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.shows.PopularTvShowsPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.shows.SearchShowsPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.shows.TopRatedTvShowsPagingSource
import com.omongole.fred.yomovieapp.data.repository.MovieRepository
import com.omongole.fred.yomovieapp.data.repository.ShowsRepository
import com.omongole.fred.yomovieapp.util.Constants.PER_PAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ShowsRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : ShowsRepository {
    override fun fetchTopRatedTvShows(): Flow<PagingData<TvShow>> {

        return Pager(
            config = PagingConfig( pageSize = PER_PAGE, prefetchDistance = 10, initialLoadSize = PER_PAGE ),
            pagingSourceFactory = { TopRatedTvShowsPagingSource( movieApi ) }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun fetchPopularTvShows(): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig( pageSize = PER_PAGE, prefetchDistance = 10, initialLoadSize = PER_PAGE ),
            pagingSourceFactory = { PopularTvShowsPagingSource( movieApi ) }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun fetchShowDetails(showId: Int): Flow<ShowDetailResponse> {
        return flow {
            emit(movieApi.fetchShowDetail(showId))
        }.flowOn(Dispatchers.IO)
    }

    override fun searchShows(query: String): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig( pageSize = PER_PAGE, prefetchDistance = 10, initialLoadSize = PER_PAGE ),
            pagingSourceFactory = { SearchShowsPagingSource( movieApi, query) }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun fetchOnAirTvShows(): Flow<PagingData<TvShow>> {
        return Pager(
            config = PagingConfig( pageSize = PER_PAGE, prefetchDistance = 10, initialLoadSize = PER_PAGE ),
            pagingSourceFactory = { OnAirTvShowsPagingSource( movieApi ) }
        ).flow.flowOn(Dispatchers.IO)
    }

}