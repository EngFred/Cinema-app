package com.omongole.fred.yomovieapp.domain.usecases

import com.omongole.fred.yomovieapp.domain.PreferenceRepository
import javax.inject.Inject

class RetrieveThemeModeUseCase @Inject constructor(
    private val prefRepository: PreferenceRepository
) {
    operator fun invoke() = prefRepository.storedThemeModel
}