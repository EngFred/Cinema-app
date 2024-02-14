package com.omongole.fred.yomovieapp.presentation.navigation

sealed class Route( val destination: String ) {
    data object Home: Route("home")
    data object Shows: Route("shows")
    data object Search: Route("search")
    data object Genre: Route("genres")
    data object MoviesGenreResult: Route("movies_genre_result")
    data object ShowsGenreResult: Route("shows_genre_result")
    data object SearchMovies: Route("sMovies")
    data object SearchShows: Route("sShows")
    data object MovieDetail: Route("mDetail")
    data object PosterImage: Route("poster_image")
    data object ShowDetail: Route("show_detail")
    data object MoviesPlayer: Route("movies_player")
}
