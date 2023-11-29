package com.omongole.fred.yomovieapp.di

import com.omongole.fred.yomovieapp.data.remote.service.ItunesApi
import com.omongole.fred.yomovieapp.data.remote.service.MovieApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component( modules = [MovieApiModule::class, ItunesAppModule::class] )
interface AppComponent {
    fun getMovieApi(): MovieApi
    fun getItunes(): ItunesApi
}