package com.omongole.fred.yomovieapp.domain.modals

import com.omongole.fred.yomovieapp.domain.modals.Itune

data class ItunesResponse(
    val resultCount: Int,
    val results: List<Itune>
)