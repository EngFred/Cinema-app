package com.omongole.fred.yomovieapp.data.repository

import com.omongole.fred.yomovieapp.data.modals.Itune
import kotlinx.coroutines.flow.Flow

interface ItunesRepository  {
    fun getItuneMovies( movieName: String ) : Flow<List<Itune>>
    fun getItuneTvShow( showName: String ) : Flow<List<Itune>>
}