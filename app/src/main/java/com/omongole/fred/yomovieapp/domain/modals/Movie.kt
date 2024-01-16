package com.omongole.fred.yomovieapp.domain.modals

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    @SerializedName("backdrop_path")
    val imagePath: String?,
    @SerializedName("original_title")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val rating: Double
)