package com.omongole.fred.yomovieapp.domain.modals.valueObjects

import com.google.gson.annotations.SerializedName

data class Network(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    val name: String
)