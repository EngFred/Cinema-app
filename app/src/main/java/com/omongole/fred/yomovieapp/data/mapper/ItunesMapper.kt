package com.omongole.fred.yomovieapp.data.mapper

import com.omongole.fred.yomovieapp.data.model.itunes.ItuneDTO
import com.omongole.fred.yomovieapp.domain.model.itune.Itune

fun ItuneDTO.toItune() : Itune {
    return Itune(
        trackCensoredName = trackCensoredName ?: "",
        previewUrl = previewUrl,
        artworkUrl100 = artworkUrl100 ?: ""
    )
}