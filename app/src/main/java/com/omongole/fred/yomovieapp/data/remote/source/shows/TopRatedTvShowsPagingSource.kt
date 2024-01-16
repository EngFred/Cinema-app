package com.omongole.fred.yomovieapp.data.remote.source.shows

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.omongole.fred.yomovieapp.domain.modals.TvShow
import com.omongole.fred.yomovieapp.data.remote.services.MovieApi
import com.omongole.fred.yomovieapp.util.Constants.PER_PAGE
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

const val TAG = "SHOWS PAGING SOURCE"

class TopRatedTvShowsPagingSource @Inject constructor (
    private val movieApi: MovieApi
)  : PagingSource<Int, TvShow>(){

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        return try {
            val currentPage = params.key ?: 1

            val apiResponse = movieApi.fetchTopRatedTvShows( page = currentPage, perPage = PER_PAGE )
            val endOfPaginationReached = apiResponse.tvShows.isEmpty()

            LoadResult.Page(
                data = apiResponse.tvShows ,
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