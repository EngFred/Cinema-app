package com.omongole.fred.yomovieapp.data.model.genres

import com.omongole.fred.yomovieapp.data.model.valueObjects.GenreDTO

data class GenresResponse(
    val genres: List<GenreDTO>
)