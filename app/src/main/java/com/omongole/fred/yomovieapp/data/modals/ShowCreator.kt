package com.omongole.fred.yomovieapp.data.modals

import com.google.gson.annotations.SerializedName

data class ShowCreator(
    val id: Int,
    val name: String,
    @SerializedName("profile_path")
    val profilePath: String?
)
