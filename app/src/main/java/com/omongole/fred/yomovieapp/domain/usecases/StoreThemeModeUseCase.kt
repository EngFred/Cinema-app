package com.omongole.fred.yomovieapp.domain.usecases

import com.omongole.fred.yomovieapp.domain.PreferenceRepository
import javax.inject.Inject

class StoreThemeModeUseCase @Inject constructor(
    private val prefRepository: PreferenceRepository
) {
    suspend operator fun invoke(themeMode: Boolean ) = prefRepository.storeThemeMode(themeMode)
}