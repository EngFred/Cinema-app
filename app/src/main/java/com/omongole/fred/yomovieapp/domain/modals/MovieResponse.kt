package com.omongole.fred.yomovieapp.domain.modals

import com.google.gson.annotations.SerializedName
import com.omongole.fred.yomovieapp.domain.modals.Movie

data class MovieResponse(
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResultS: Int,
    @SerializedName("results")
    val movies: List<Movie>
)



