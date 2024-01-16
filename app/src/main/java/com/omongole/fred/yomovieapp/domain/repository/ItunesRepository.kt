package com.omongole.fred.yomovieapp.domain.repository

import com.omongole.fred.yomovieapp.domain.modals.Itune
import kotlinx.coroutines.flow.Flow

interface ItunesRepository  {
    fun getItuneMovies( movieName: String ) : Flow<List<Itune>>
    fun getItuneTvShow( showName: String ) : Flow<List<Itune>>
}