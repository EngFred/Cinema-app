package com.omongole.fred.yomovieapp.data.model.movies

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResultS: Int,
    @SerializedName("results")
    val movies: List<MovieDto>
)



