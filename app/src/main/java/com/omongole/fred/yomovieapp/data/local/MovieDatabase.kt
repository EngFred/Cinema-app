package com.omongole.fred.yomovieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omongole.fred.yomovieapp.data.local.dao.MovieDao
import com.omongole.fred.yomovieapp.data.modals.Movie

@Database(entities = [Movie::class], version = 3, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao() : MovieDao
}