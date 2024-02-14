package com.omongole.fred.yomovieapp.presentation.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.omongole.fred.yomovieapp.presentation.navigation.BottomBarItem

@Composable
fun AppBottomBar(
    navController: NavHostController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if ( BottomBarItem().getBottomNavigationItems().any { it.route == currentDestination?.route } ) {
        NavigationBar{
            BottomBarItem().getBottomNavigationItems().forEachIndexed { index, item ->
                //iterating all items with their respective indexes
                NavigationBarItem(
                    selected = currentDestination?.route == item.route,
                    label = { Text(text = item.label) },
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = item.label
                        )
                    },
                    onClick = {
                        navController.navigate( item.route ) {
                            popUpTo(navController.graph.findStartDestination().id){ saveState =  true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

}