package com.omongole.fred.yomovieapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.omongole.fred.yomovieapp.presentation.navigation.Route

data class BottomBarNavigationItem(
    val label : String = "",
    val icon : ImageVector = Icons.Filled.Home,
    val route : String = "",
) {

    fun getBottomNavigationItems() : List<BottomBarNavigationItem> {
        return listOf(
            BottomBarNavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                route = Route.Home.destination
            ),
            BottomBarNavigationItem(
                label = "Find",
                icon = Icons.Filled.Search,
                route = Route.Search.destination
            ),
            BottomBarNavigationItem(
                label = "Shows",
                icon = Icons.Filled.DateRange,
                route = Route.Shows.destination
            ),
            BottomBarNavigationItem(
                label = "Genres",
                icon = Icons.Filled.Menu,
                route = Route.Genre.destination
            )
        )
    }
}