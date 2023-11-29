package com.omongole.fred.yomovieapp.domain.usecases.shows

import com.omongole.fred.yomovieapp.data.repository.ShowsRepository
import javax.inject.Inject

class GetShowsByGenreUseCase @Inject constructor(
    private val showsRepository: ShowsRepository
) {
    operator fun invoke( genreId: Long ) = showsRepository.fetchShowsByGenre(genreId)
}