package com.omongole.fred.yomovieapp.data.remote.source.movies

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.omongole.fred.yomovieapp.data.modals.Movie
import com.omongole.fred.yomovieapp.data.remote.MovieApi
import com.omongole.fred.yomovieapp.util.Constants.PER_PAGE
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class PopularMoviesPagingSource @Inject constructor (
    private val movieApi: MovieApi
)  : PagingSource<Int, Movie>(){

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val currentPage = params.key ?: 1

            val apiResponse = movieApi.fetchPopularMovies( page = currentPage, perPage = PER_PAGE )
            val endOfPaginationReached = apiResponse.movies.isEmpty()

            LoadResult.Page(
                data = apiResponse.movies ,
                prevKey = if ( currentPage == 1 ) null else currentPage - 1,
                nextKey = if ( endOfPaginationReached ) null else currentPage + 1
            )

        } catch ( ex: UnknownHostException ) {
            Log.d(TAG, "$ex")
            LoadResult.Error(ex)
        } catch ( ex: HttpException ) {
            Log.d(TAG, "$ex")
            LoadResult.Error(ex)
        } catch ( ex: SocketTimeoutException ) {
            Log.d(TAG, "$ex")
            LoadResult.Error(ex)
        } catch ( ex: ConnectException ) {
            Log.d(TAG, "$ex")
            LoadResult.Error(ex)
        }
    }

}