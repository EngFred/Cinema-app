package com.omongole.fred.yomovieapp.domain.modals

import com.google.gson.annotations.SerializedName
import com.omongole.fred.yomovieapp.domain.modals.valueObjects.Company
import com.omongole.fred.yomovieapp.domain.modals.valueObjects.Country
import com.omongole.fred.yomovieapp.domain.modals.valueObjects.Genre

data class MovieDetailResponse(
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("original_language")
    val language: String,
    val title: String,
    @SerializedName("overview")
    val overView: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<Company>,
    @SerializedName("production_countries")
    val productionCountries: List<Country>,
    @SerializedName("release_date")
    val releaseDate: String?,
    val revenue: Long,
    @SerializedName("runtime")
    val duration: String,
    val status: String?,
    val tagline: String?,
    @SerializedName("vote_average")
    val rating: Double
)