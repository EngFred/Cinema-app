package com.omongole.fred.yomovieapp.domain.repository

import androidx.paging.PagingData
import com.omongole.fred.yomovieapp.domain.modals.valueObjects.Genre
import com.omongole.fred.yomovieapp.domain.modals.Movie
import com.omongole.fred.yomovieapp.domain.modals.MovieDetailResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getUpComingMovies() : Flow<PagingData<Movie>>
    fun getNowPlayingMovies() : Flow<PagingData<Movie>>
    fun getPopularMovies() : Flow<PagingData<Movie>>
    fun getTopRatedMovies() : Flow<PagingData<Movie>>
    fun getMoviesGenre() : Flow<List<Genre>>
    fun getMoviesByGenre( genreId: Long ) : Flow<PagingData<Movie>>
    fun getTrendingMovies() : Flow<PagingData<Movie>>
    fun searchMovies( query: String ) : Flow<PagingData<Movie>>
    fun getMovieDetail( movieId: Int ) : Flow<MovieDetailResponse>
}