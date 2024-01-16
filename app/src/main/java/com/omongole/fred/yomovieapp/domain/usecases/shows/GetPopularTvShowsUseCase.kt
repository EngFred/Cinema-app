package com.omongole.fred.yomovieapp.domain.usecases.shows

import com.omongole.fred.yomovieapp.domain.repository.ShowsRepository
import javax.inject.Inject

class GetPopularTvShowsUseCase @Inject constructor(
    private val showsRepository: ShowsRepository
) {
    operator fun invoke() = showsRepository.fetchPopularTvShows()
}