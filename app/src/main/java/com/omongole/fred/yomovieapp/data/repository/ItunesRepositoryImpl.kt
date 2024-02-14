package com.omongole.fred.yomovieapp.data.repository

import android.util.Log
import com.omongole.fred.yomovieapp.data.mapper.toItune
import com.omongole.fred.yomovieapp.data.model.itunes.ItuneDTO
import com.omongole.fred.yomovieapp.data.remote.services.ItunesApi
import com.omongole.fred.yomovieapp.domain.model.itune.Itune
import com.omongole.fred.yomovieapp.domain.repository.ItunesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ItunesRepositoryImpl @Inject constructor(
    private val itunesApi: ItunesApi
) : ItunesRepository {

    companion object {
        const val TAG = "ItunesRepositoryImp"
    }
    override fun getItuneMovies(movieName: String ): Flow<List<Itune>> {
        return flow {
            emit( itunesApi.searchItuneMovie( movieName ).results.map { it.toItune() } )
        }.flowOn(Dispatchers.IO).catch {
            Log.i(TAG, "$it")
        }
    }

    override fun getItuneTvShow(showName: String): Flow<List<Itune>> {
        return flow {
            emit( itunesApi.searchItuneTvShow ( showName ).results.map { it.toItune() } )
        }.flowOn(Dispatchers.IO).catch {
            Log.i(TAG, "$it")
        }
    }

}