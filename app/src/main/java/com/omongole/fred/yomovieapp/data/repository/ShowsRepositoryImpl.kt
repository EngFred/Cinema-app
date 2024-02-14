package com.omongole.fred.yomovieapp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.omongole.fred.yomovieapp.data.mapper.toGenre
import com.omongole.fred.yomovieapp.data.mapper.toShow
import com.omongole.fred.yomovieapp.data.mapper.toShowDetail
import com.omongole.fred.yomovieapp.data.remote.services.MovieApi
import com.omongole.fred.yomovieapp.data.remote.source.shows.OnAirTvShowsPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.shows.PopularTvShowsPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.shows.SearchShowsPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.shows.ShowsByGenrePagingSource
import com.omongole.fred.yomovieapp.data.remote.source.shows.TopRatedTvShowsPagingSource
import com.omongole.fred.yomovieapp.domain.model.shows.Show
import com.omongole.fred.yomovieapp.domain.model.shows.ShowDetail
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Genre
import com.omongole.fred.yomovieapp.domain.repository.ShowsRepository
import com.omongole.fred.yomovieapp.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShowsRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
) : ShowsRepository {

    companion object {
        const val TAG = "ShowsRepositoryImpl"
    }

    override fun fetchTopRatedTvShows(): Flow<PagingData<Show>> {
        return Pager(
            config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
            pagingSourceFactory = { TopRatedTvShowsPagingSource( movieApi ) }
        ).flow.map {
            it.map {
                it.toShow()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun fetchPopularTvShows(): Flow<PagingData<Show>> {
        return Pager(
            config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
            pagingSourceFactory = { PopularTvShowsPagingSource( movieApi ) }
        ).flow.map {
            it.map {
                it.toShow()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun fetchShowDetails(showId: Int): Flow<ShowDetail> {
        return flow {
            emit( movieApi.fetchShowDetail(showId).toShowDetail() )
        }.flowOn(Dispatchers.IO).catch {
            Log.i(TAG, "$it")
        }
    }

    override fun fetchShowsByGenre(genreId: Long): Flow<PagingData<Show>> {
        return Pager(
            config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
            pagingSourceFactory = { ShowsByGenrePagingSource( movieApi, genreId ) }
        ).flow.map {
            it.map {
                it.toShow()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getShowsGenres(): Flow<List<Genre>> {
        return flow {
            emit( movieApi.fetchShowsGenre().genres.map { it.toGenre() } )
        }.flowOn( Dispatchers.IO ).catch {
            Log.i(TAG, "$it")
        }
    }

    override fun searchShows(query: String): Flow<PagingData<Show>> {
        return Pager(
            config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
            pagingSourceFactory = { SearchShowsPagingSource( movieApi, query) }
        ).flow.map {
            it.map {
                it.toShow()
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun fetchOnAirTvShows(): Flow<PagingData<Show>> {
        return Pager(
            config = PagingConfig( pageSize = ITEMS_PER_PAGE, prefetchDistance = 10, initialLoadSize = ITEMS_PER_PAGE ),
            pagingSourceFactory = { OnAirTvShowsPagingSource( movieApi ) }
        ).flow.map {
            it.map {
                it.toShow()
            }
        }.flowOn(Dispatchers.IO)
    }

}