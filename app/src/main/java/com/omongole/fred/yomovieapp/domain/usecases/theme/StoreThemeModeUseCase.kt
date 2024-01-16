package com.omongole.fred.yomovieapp.domain.usecases.theme

import com.omongole.fred.yomovieapp.data.cache.PreferenceRepository
import javax.inject.Inject

class StoreThemeModeUseCase @Inject constructor(
    private val prefRepository: PreferenceRepository
) {
    suspend operator fun invoke(themeMode: Boolean ) = prefRepository.storeThemeMode(themeMode)
}