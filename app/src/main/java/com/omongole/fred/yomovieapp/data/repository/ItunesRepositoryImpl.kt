package com.omongole.fred.yomovieapp.data.repository

import com.omongole.fred.yomovieapp.domain.modals.Itune
import com.omongole.fred.yomovieapp.data.remote.services.ItunesApi
import com.omongole.fred.yomovieapp.domain.repository.ItunesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ItunesRepositoryImpl @Inject constructor(
    private val itunesApi: ItunesApi
) : ItunesRepository {
    override fun getItuneMovies(movieName: String ): Flow<List<Itune>> {
        return flow {
            emit( itunesApi.searchItuneMovie( movieName ).results )
        }.flowOn(Dispatchers.IO)
    }

    override fun getItuneTvShow(showName: String): Flow<List<Itune>> {
        return flow {
            emit( itunesApi.searchItuneTvShow ( showName ).results )
        }.flowOn(Dispatchers.IO)
    }

}