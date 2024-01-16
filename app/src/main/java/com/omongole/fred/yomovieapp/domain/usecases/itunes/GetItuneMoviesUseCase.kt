package com.omongole.fred.yomovieapp.domain.usecases.itunes

import com.omongole.fred.yomovieapp.domain.repository.ItunesRepository
import javax.inject.Inject

class GetItuneMoviesUseCase @Inject constructor(
    private val itunesRepository: ItunesRepository
) {
    operator fun invoke( movieName: String ) = itunesRepository.getItuneMovies(movieName)
}