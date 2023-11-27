package com.omongole.fred.yomovieapp.domain.usecases.movies

import com.omongole.fred.yomovieapp.data.repository.MovieRepository
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() = movieRepository.getTopRatedMovies()
}