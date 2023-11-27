package com.omongole.fred.yomovieapp.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.omongole.fred.yomovieapp.data.local.MovieDatabase
import com.omongole.fred.yomovieapp.data.local.dao.MovieDao
import com.omongole.fred.yomovieapp.data.remote.MovieApi
import com.omongole.fred.yomovieapp.data.repository.MovieRepository
import com.omongole.fred.yomovieapp.data.repository.ShowsRepository
import com.omongole.fred.yomovieapp.domain.MovieRepositoryImpl
import com.omongole.fred.yomovieapp.domain.ShowsRepositoryImpl
import com.omongole.fred.yomovieapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val httpClient = OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor)
        httpClient.readTimeout(60, TimeUnit.SECONDS)

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApiInstance( retrofit: Retrofit ): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMovieDatabase( @ApplicationContext context: Context ): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java, "movies_database").fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideMoviesDao(movieDatabase: MovieDatabase ): MovieDao =  movieDatabase.getMovieDao()

    @Singleton
    @Provides
    fun providesMovieRepository( api: MovieApi ) : MovieRepository = MovieRepositoryImpl( api )

    @Singleton
    @Provides
    fun providesShowsRepository( api: MovieApi ) : ShowsRepository = ShowsRepositoryImpl( api )

    @Singleton
    @Provides
    fun provideDatastoreInstance(  @ApplicationContext context : Context  ) = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("settings")
    }

}