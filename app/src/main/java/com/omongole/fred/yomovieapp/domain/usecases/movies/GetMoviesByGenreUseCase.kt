package com.omongole.fred.yomovieapp.domain.usecases.movies

import com.omongole.fred.yomovieapp.data.repository.MovieRepository
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke( genreId: Long ) = movieRepository.getMoviesByGenre(genreId)
}