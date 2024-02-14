package com.omongole.fred.yomovieapp.domain.model.movies

import com.omongole.fred.yomovieapp.domain.model.valueObjects.Company
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Country
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Genre

data class MovieDetail(
    val id: Int,
    val budget: Long,
    val genres: List<Genre>,
    val language: String,
    val title: String,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val productionCompanies: List<Company>,
    val productionCountries: List<Country>,
    val releaseDate: String?,
    val revenue: Long,
    val duration: String,
    val status: String?,
    val tagline: String?,
    val rating: Double
)