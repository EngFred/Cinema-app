package com.omongole.fred.yomovieapp.data.remote.service

import com.omongole.fred.yomovieapp.data.remote.ItunesApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {
    @GET("search?media=movie")
    suspend fun searchItuneMovie(
        @Query("term") name: String
    ) : ItunesApiResponse

    @GET("search?media=tvShow")
    suspend fun searchItuneTvShow(
        @Query("term") name: String
    ) : ItunesApiResponse
}