package com.omongole.fred.yomovieapp.domain.usecases.shows

import com.omongole.fred.yomovieapp.data.repository.ShowsRepository
import javax.inject.Inject

class GetTopRatedTvShowsUseCase @Inject constructor(
    private val showsRepository: ShowsRepository
) {
    operator fun invoke() = showsRepository.fetchTopRatedTvShows()
}