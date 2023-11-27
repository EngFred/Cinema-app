package com.omongole.fred.yomovieapp.data.modals

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie(
    @PrimaryKey( autoGenerate = false )
    val id: Int,
    @SerializedName("backdrop_path")
    val imagePath: String?,
    @SerializedName("original_title")
    val title: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val rating: Double
)