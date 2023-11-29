package com.omongole.fred.yomovieapp.data.remote

import com.omongole.fred.yomovieapp.data.modals.Itune

data class ItunesApiResponse(
    val resultCount: Int,
    val results: List<Itune>
)

data class ItunesTvShowsApiResponse(
    val resultCount: Int,
    val results: List<Itune>
)