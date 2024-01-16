package com.omongole.fred.yomovieapp.domain.modals

import com.google.gson.annotations.SerializedName

data class ShowResponse(
    val page: Int,
    @SerializedName("results")
    val tvShows: List<TvShow>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResultS: Int,
)