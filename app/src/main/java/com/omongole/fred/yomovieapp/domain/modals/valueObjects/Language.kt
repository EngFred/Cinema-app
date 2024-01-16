package com.omongole.fred.yomovieapp.domain.modals.valueObjects

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("english_name")
    val name: String,
)
