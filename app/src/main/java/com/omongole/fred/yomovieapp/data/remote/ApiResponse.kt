package com.omongole.fred.yomovieapp.data.remote

import com.google.gson.annotations.SerializedName
import com.omongole.fred.yomovieapp.data.modals.Company
import com.omongole.fred.yomovieapp.data.modals.Country
import com.omongole.fred.yomovieapp.data.modals.Genre
import com.omongole.fred.yomovieapp.data.modals.Language
import com.omongole.fred.yomovieapp.data.modals.Movie
import com.omongole.fred.yomovieapp.data.modals.Network
import com.omongole.fred.yomovieapp.data.modals.ShowCreator
import com.omongole.fred.yomovieapp.data.modals.TvShow

data class MoviesResponse(
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResultS: Int,
    @SerializedName("results")
    val movies: List<Movie>
)
data class ShowResponse(
    val page: Int,
    @SerializedName("results")
    val tvShows: List<TvShow>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResultS: Int,
)

data class GenresResponse(
    val genres: List<Genre>
)

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

data class MovieDetailResponse(
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    @SerializedName("original_language")
    val language: String,
    @SerializedName("original_title")
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

