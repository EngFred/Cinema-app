package com.omongole.fred.yomovieapp.data.remote.services

import com.omongole.fred.yomovieapp.data.model.itunes.ItunesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {
    @GET("search?media=movie")
    suspend fun searchItuneMovie(
        @Query("term") name: String
    ) : ItunesResponse

    @GET("search?media=tvShow")
    suspend fun searchItuneTvShow(
        @Query("term") name: String
    ) : ItunesResponse
}