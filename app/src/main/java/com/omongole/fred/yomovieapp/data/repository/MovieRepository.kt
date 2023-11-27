package com.omongole.fred.yomovieapp.data.repository

import androidx.paging.PagingData
import com.omongole.fred.yomovieapp.data.modals.Genre
import com.omongole.fred.yomovieapp.data.modals.Movie
import com.omongole.fred.yomovieapp.data.remote.MovieDetailResponse
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