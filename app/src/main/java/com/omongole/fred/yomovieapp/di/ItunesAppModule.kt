package com.omongole.fred.yomovieapp.di

import com.omongole.fred.yomovieapp.data.remote.services.ItunesApi
import com.omongole.fred.yomovieapp.domain.repository.ItunesRepository
import com.omongole.fred.yomovieapp.data.repository.ItunesRepositoryImpl
import com.omongole.fred.yomovieapp.util.Constants.ITUNES_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ItunesAppModule {

    @Singleton
    @Provides
    @JvmStatic
    fun providesItunesApiInstance() : ItunesApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val httpClient = OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor)
        httpClient.readTimeout(60, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(ITUNES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(ItunesApi::class.java)
    }

    @Singleton
    @Provides
    fun providesItunesRepository( api: ItunesApi ) : ItunesRepository = ItunesRepositoryImpl( api )

}