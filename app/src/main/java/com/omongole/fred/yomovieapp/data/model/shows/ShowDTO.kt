package com.omongole.fred.yomovieapp.data.model.shows

data class ShowDTO(
    val id: Int,
    val name:String?,
    val poster_path: String?,
    val vote_average: Double?,
    val first_air_date: String?
)
