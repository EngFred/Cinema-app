package com.omongole.fred.yomovieapp.data.model.itunes

data class ItunesResponse(
    val resultCount: Int,
    val results: List<ItuneDTO>
)