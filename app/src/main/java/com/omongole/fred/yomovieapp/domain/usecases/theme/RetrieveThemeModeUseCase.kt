package com.omongole.fred.yomovieapp.domain.usecases.theme

import com.omongole.fred.yomovieapp.data.cache.PreferenceRepository
import javax.inject.Inject

class RetrieveThemeModeUseCase @Inject constructor(
    private val prefRepository: PreferenceRepository
) {
    operator fun invoke() = prefRepository.storedThemeModel
}