package com.omongole.fred.yomovieapp.data.modals

import com.google.gson.annotations.SerializedName

data class TvShow(
    val name:String,
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("vote_average")
    val rating: Double,
    @SerializedName("first_air_date")
    val firstAirDate: String
)
