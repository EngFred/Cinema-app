package com.omongole.fred.yomovieapp.domain.model.shows

import com.omongole.fred.yomovieapp.domain.model.valueObjects.Company
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Country
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Genre
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Language
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Network
import com.omongole.fred.yomovieapp.domain.model.valueObjects.ShowCreator

data class ShowDetail(
    val id: Int,
    val createdBy: List<ShowCreator>,
    val firstAirDate: String,
    val lastAirDate: String,
    val genres: List<Genre>,
    val spokenLanguages: List<Language>,
    val name: String,
    val networks: List<Network>,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val productionCompanies: List<Company>,
    val productionCountries: List<Country>,
    val posterPath: String?,
    val backdropPath: String?,
    val overview: String,
    val status: String?,
    val tagline: String?,
    val rating: Double
)