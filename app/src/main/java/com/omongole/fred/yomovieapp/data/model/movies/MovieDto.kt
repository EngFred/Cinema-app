package com.omongole.fred.yomovieapp.data.model.movies

data class MovieDto(
    val id: Int,
    val backdrop_path: String?,
    val title: String?,
    val poster_path: String?,
    val release_date: String?,
    val vote_average: Double?
)