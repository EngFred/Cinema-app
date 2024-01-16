package com.omongole.fred.yomovieapp.domain.modals

import com.google.gson.annotations.SerializedName

data class TvShow(
    val id: Int,
    val name:String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("first_air_date")
    val firstAirDate: String
)
