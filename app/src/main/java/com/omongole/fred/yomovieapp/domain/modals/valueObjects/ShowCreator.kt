package com.omongole.fred.yomovieapp.domain.modals.valueObjects

import com.google.gson.annotations.SerializedName

data class ShowCreator(
    val id: Int,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String?
)
