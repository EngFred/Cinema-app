package com.omongole.fred.yomovieapp.data.model.movies

import com.omongole.fred.yomovieapp.data.model.valueObjects.CompanyDTO
import com.omongole.fred.yomovieapp.data.model.valueObjects.CountryDTO
import com.omongole.fred.yomovieapp.data.model.valueObjects.GenreDTO

data class MovieDetailDTO(
    val id: Int,
    val budget: Long?,
    val genres: List<GenreDTO>,
    val original_language: String?,
    val title: String?,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val production_companies: List<CompanyDTO>,
    val production_countries: List<CountryDTO>,
    val release_date: String?,
    val revenue: Long?,
    val runtime: String?,
    val status: String?,
    val tagline: String?,
    val vote_average: Double?
)