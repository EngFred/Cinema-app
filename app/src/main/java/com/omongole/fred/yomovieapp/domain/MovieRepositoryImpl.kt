package com.omongole.fred.yomovieapp.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.omongole.fred.yomovieapp.data.modals.Genre
import com.omongole.fred.yomovieapp.data.modals.Movie
import com.omongole.fred.yomovieapp.data.remote.MovieApi
import com.omongole.fred.yomovieapp.data.remote.MovieDetailResponse
import com.omongole.fred.yomovieapp.data.remote.source.movies.MoviesByGenrePagingSource
import com.omongole.fred.yomovieapp.data.remote.source.movies.NowPlayingMoviesPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.movies.PopularMoviesPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.movies.SearchMoviesPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.movies.TopRatedMoviesPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.movies.TrendingMoviesPagingSource
import com.omongole.fred.yomovieapp.data.remote.source.movies.UpComingMoviesPagingSource
import com.omongole.fred.yomovieapp.data.repository.MovieRepository
import com.omongole.fred.yomovieapp.util.Constants.PER_PAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    override fun getUpComingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = PER_PAGE, prefetchDistance = 10, initialLoadSize = PER_PAGE ),
            pagingSourceFactory = { UpComingMoviesPagingSource( movieApi ) },
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = PER_PAGE, prefetchDistance = 10, initialLoadSize = PER_PAGE ),
            pagingSourceFactory = { NowPlayingMoviesPagingSource( movieApi ) },
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = PER_PAGE, prefetchDistance = 10, initialLoadSize = PER_PAGE ),
            pagingSourceFactory = { PopularMoviesPagingSource( movieApi ) },
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getMoviesGenre(): Flow<List<Genre>> {
        return flow {
            emit( movieApi.fetchMoviesGenre().genres )
        }.flowOn(Dispatchers.IO)
    }

    override fun getMovieDetail(movieId: Int): Flow<MovieDetailResponse> {
        return flow {
            emit( movieApi.fetchMovieDetail(movieId) )
        }.flowOn(Dispatchers.IO)
    }

    override fun searchMovies( query: String ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = PER_PAGE, prefetchDistance = 10, initialLoadSize = PER_PAGE ),
            pagingSourceFactory = { SearchMoviesPagingSource( movieApi, query ) },
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getTrendingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = PER_PAGE, prefetchDistance = 10, initialLoadSize = PER_PAGE ),
            pagingSourceFactory = { TrendingMoviesPagingSource(movieApi) },
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getMoviesByGenre( genreId: Long ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = PER_PAGE, prefetchDistance = 10, initialLoadSize = PER_PAGE ),
            pagingSourceFactory = { MoviesByGenrePagingSource( movieApi, genreId ) },
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig( pageSize = PER_PAGE, prefetchDistance = 10, initialLoadSize = PER_PAGE ),
            pagingSourceFactory = { TopRatedMoviesPagingSource( movieApi ) },
        ).flow.flowOn(Dispatchers.IO)
    }
}