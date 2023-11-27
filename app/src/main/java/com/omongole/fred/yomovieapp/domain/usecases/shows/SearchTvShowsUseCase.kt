package com.omongole.fred.yomovieapp.domain.usecases.shows

import com.omongole.fred.yomovieapp.data.repository.ShowsRepository
import javax.inject.Inject

class SearchTvShowsUseCase @Inject constructor(
    private val showsRepository: ShowsRepository,
) {
    operator fun invoke(query: String) = showsRepository.searchShows( query )
}