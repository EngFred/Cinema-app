package com.omongole.fred.yomovieapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.omongole.fred.yomovieapp.presentation.screens.PosterScreen
import com.omongole.fred.yomovieapp.presentation.screens.SharedViewModel
import com.omongole.fred.yomovieapp.presentation.screens.detail.MovieDetailScreen
import com.omongole.fred.yomovieapp.presentation.screens.detail.MovieDetailScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.screens.detail.ShowDetailScreen
import com.omongole.fred.yomovieapp.presentation.screens.detail.ShowDetailScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.screens.genres.GenreResultScreen
import com.omongole.fred.yomovieapp.presentation.screens.genres.GenreResultViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.screens.genres.GenresScreen
import com.omongole.fred.yomovieapp.presentation.screens.home.HomeScreen
import com.omongole.fred.yomovieapp.presentation.screens.search.MoviesSearchResultScreen
import com.omongole.fred.yomovieapp.presentation.screens.search.MoviesSearchResultScreenViewModelAssistedFactory
import com.omongole.fred.yomovieapp.presentation.screens.search.SearchScreen
import com.omongole.fred.yomovieapp.presentation.screens.shows.ShowsScreen
import com.omongole.fred.yomovieapp.presentation.screens.shows.ShowsSearchResultScreen
import com.omongole.fred.yomovieapp.presentation.screens.shows.ShowsSearchResultScreenViewModelAssistedFactory

@Composable
fun AppNavigationGraph(
    navHostController: NavHostController,
    genresAssistedFactory: GenreResultViewModelAssistedFactory,
    moviesSearchAssistedFactory: MoviesSearchResultScreenViewModelAssistedFactory,
    showsSearchAssistedFactory: ShowsSearchResultScreenViewModelAssistedFactory,
    movieDetailAssistedFactory: MovieDetailScreenViewModelAssistedFactory,
    showDetailAssistedFactory: ShowDetailScreenViewModelAssistedFactory,
    modifier: Modifier,
    sharedViewModel: SharedViewModel,
    darkTheme: Boolean
) {
    NavHost(
        navController = navHostController,
        startDestination = Route.Home.destination
    ) {

        composable(route= Route.Home.destination ) {
            HomeScreen( modifier = modifier, showMovieDetail = {
                navHostController.navigate("${Route.MovieDetail.destination}/$it")
            },darkTheme = darkTheme )
        }

        composable(route= Route.Search.destination ) {
            SearchScreen(modifier = modifier, searchMovies = {
                navHostController.navigate("${Route.SearchMovies.destination}/$it")
            }, showMovieDetail = {
                navHostController.navigate("${Route.MovieDetail.destination}/$it")
            })
        }

        composable(
            route = "${Route.SearchMovies.destination}/{query}",
            arguments = listOf(
                navArgument("query"){ type = NavType.StringType }
            )
        ) {
            val query = it.arguments?.getString("query")!!
            MoviesSearchResultScreen( query, moviesSearchAssistedFactory, modifier, showMovieDetail = {movieId ->
                navHostController.navigate("${Route.MovieDetail.destination}/$movieId")
            }, showMoviePoster = {posterPath ->
                sharedViewModel.putPosterPath(posterPath)
                navHostController.navigate(Route.PosterImage.destination)
            } )
        }

        composable(route= Route.Shows.destination ) {
            ShowsScreen(modifier = modifier, searchShows = {
                navHostController.navigate("${Route.SearchShows.destination}/$it")
            }, showDetails = {showId ->
                navHostController.navigate("${Route.ShowDetail.destination}/$showId")
            })
        }

        composable(
            route = "${Route.SearchShows.destination}/{q}",
            arguments = listOf(
                navArgument(name= "q"){  type = NavType.StringType }
            )
        ){
            val query = it.arguments?.getString("q")!!
            ShowsSearchResultScreen(query = query, assistedFactory = showsSearchAssistedFactory, modifier = modifier, showDetails = {
                navHostController.navigate("${Route.ShowDetail.destination}/$it")
            }, showPoster = {
                sharedViewModel.putPosterPath(it)
                navHostController.navigate(Route.PosterImage.destination)
            })
        }

        composable(route= Route.Genre.destination ) {
            GenresScreen(modifier = modifier, fetchMoviesByGenre = {
                navHostController.navigate("${Route.GenreMovies.destination}/$it")
            })
        }

        composable(
            route = Route.GenreMovies.destination+ "/{id}",
            arguments =  listOf(
                navArgument(name = "id") {
                    type = NavType.LongType
                }
            )
        ) {
            val id = it.arguments?.getLong("id")!!
            GenreResultScreen(modifier = modifier, genreId = id, assistedFactory = genresAssistedFactory, showMovieDetail = {movieId ->
                navHostController.navigate("${Route.MovieDetail.destination}/$movieId")
            }, showMoviePoster = {posterPath ->
                sharedViewModel.putPosterPath(posterPath)
                navHostController.navigate(Route.PosterImage.destination)
            } )
        }

        composable(route = Route.PosterImage.destination) {
            PosterScreen(sharedViewModel = sharedViewModel)
        }

        composable(
            route = "${Route.MovieDetail.destination}/{id}",
            arguments = listOf(
                navArgument(name = "id"){ type = NavType.IntType }
            )
        ) {
            val movieId = it.arguments?.getInt("id")!!
            MovieDetailScreen(movieId = movieId, modifier = modifier, assistedFactory = movieDetailAssistedFactory, showMoviePoster = { posterPath ->
                sharedViewModel.putPosterPath(posterPath)
                navHostController.navigate(Route.PosterImage.destination)
            } )
        }

        composable(
            route = "${Route.ShowDetail.destination}/{id}",
            arguments = listOf(
                navArgument(name="id") { type = NavType.IntType }
            )
        ) {
            val showId = it.arguments?.getInt("id")!!
            ShowDetailScreen(showId = showId, modifier = modifier, assistedFactory = showDetailAssistedFactory, showPoster = { posterPath ->
                sharedViewModel.putPosterPath(posterPath)
                navHostController.navigate(Route.PosterImage.destination)
            } )
        }
    }

}