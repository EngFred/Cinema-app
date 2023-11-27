package com.omongole.fred.yomovieapp.data.modals

import com.google.gson.annotations.SerializedName

data class Network(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    val name: String
)
