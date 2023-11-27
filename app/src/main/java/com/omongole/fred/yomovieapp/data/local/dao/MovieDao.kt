package com.omongole.fred.yomovieapp.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.omongole.fred.yomovieapp.data.modals.Movie

@Dao
interface MovieDao {

    @Upsert
    suspend fun insertMovies(movies: List<Movie> )

    @Query("SELECT * FROM movie ORDER BY popularity ASC")
    fun getMovies() : PagingSource<Int, Movie>

    @Query("DELETE FROM movie")
    suspend fun clearAllMovies()

}