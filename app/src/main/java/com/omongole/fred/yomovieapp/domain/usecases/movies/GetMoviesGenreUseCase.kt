package com.omongole.fred.yomovieapp.domain.usecases.movies

import com.omongole.fred.yomovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke() = movieRepository.getMoviesGenre()
}