package com.omongole.fred.yomovieapp.domain.modals

import com.google.gson.annotations.SerializedName
import com.omongole.fred.yomovieapp.domain.modals.valueObjects.Company
import com.omongole.fred.yomovieapp.domain.modals.valueObjects.Country
import com.omongole.fred.yomovieapp.domain.modals.valueObjects.Genre
import com.omongole.fred.yomovieapp.domain.modals.valueObjects.Language
import com.omongole.fred.yomovieapp.domain.modals.valueObjects.Network
import com.omongole.fred.yomovieapp.domain.modals.valueObjects.ShowCreator

data class ShowDetailResponse(
    val id: Int,
    @SerializedName("created_by")
    val createdBy: List<ShowCreator>,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("last_air_date")
    val lastAirDate: String,
    val genres: List<Genre>,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<Language>,
    val name: String,
    val networks: List<Network>,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int,
    @SerializedName("production_companies")
    val productionCompanies: List<Company>,
    @SerializedName("production_countries")
    val productionCountries: List<Country>,
    @SerializedName("poster_path")
    val posterPath: String?,
    val overview: String,
    val status: String?,
    val tagline: String?,
    @SerializedName("vote_average")
    val rating: Double
)