package com.omongole.fred.yomovieapp.data.model.shows

import com.omongole.fred.yomovieapp.data.model.valueObjects.CompanyDTO
import com.omongole.fred.yomovieapp.data.model.valueObjects.CountryDTO
import com.omongole.fred.yomovieapp.data.model.valueObjects.GenreDTO
import com.omongole.fred.yomovieapp.data.model.valueObjects.LanguageDTO
import com.omongole.fred.yomovieapp.data.model.valueObjects.NetworkDTO
import com.omongole.fred.yomovieapp.data.model.valueObjects.ShowCreatorDTO

data class ShowDetailDTO(
    val id: Int,
    val created_by: List<ShowCreatorDTO>,
    val first_air_date: String?,
    val last_air_date: String?,
    val genres: List<GenreDTO>,
    val spoken_languages: List<LanguageDTO>,
    val name: String?,
    val networks: List<NetworkDTO>,
    val number_of_episodes: Int?,
    val number_of_seasons: Int?,
    val production_companies: List<CompanyDTO>,
    val production_countries: List<CountryDTO>,
    val poster_path: String?,
    val backdrop_path: String?,
    val overview: String?,
    val status: String?,
    val tagline: String?,
    val vote_average: Double?
)