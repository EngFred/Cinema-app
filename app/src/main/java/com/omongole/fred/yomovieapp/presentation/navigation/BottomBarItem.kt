package com.omongole.fred.yomovieapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tv
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = "",
) {

    fun getBottomNavigationItems() : List<BottomBarItem> {
        return listOf(
            BottomBarItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Route.Home.destination
            ),
            BottomBarItem(
                label = "Find",
                icon = Icons.Filled.Search,
                route = Route.Search.destination
            ),
            BottomBarItem(
                label = "Shows",
                icon = Icons.Filled.Tv,
                route = Route.Shows.destination
            ),
            BottomBarItem(
                label = "Genres",
                icon = Icons.Filled.Movie,
                route = Route.Genre.destination
            )
        )
    }
}