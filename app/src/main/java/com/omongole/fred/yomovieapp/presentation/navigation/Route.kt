package com.omongole.fred.yomovieapp.presentation.navigation

sealed class Route( val destination: String ) {
    object Home: Route("home")
    object Shows: Route("shows")
    object Search: Route("search")
    object Genre: Route("genres")
    object GenreMovies: Route("gMovies")
    object SearchMovies: Route("sMovies")
    object SearchShows: Route("sShows")
    object MovieDetail: Route("mDetail")
    object PosterImage: Route("poster_image")
    object ShowDetail: Route("show_detail")
}
