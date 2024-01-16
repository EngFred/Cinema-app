package com.omongole.fred.yomovieapp.domain.modals

import com.omongole.fred.yomovieapp.domain.modals.valueObjects.Genre

data class GenresResponse(
    val genres: List<Genre>
)