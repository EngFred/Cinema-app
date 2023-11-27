package com.omongole.fred.yomovieapp.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppBottomBar(
    navController: NavHostController
) {

    var selectedBottomBarItem by remember {
        mutableStateOf(0)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    if ( BottomBarNavigationItem().getBottomNavigationItems().any { it.route == currentDestination?.route } ) {
        NavigationBar{
            BottomBarNavigationItem().getBottomNavigationItems().forEachIndexed { index, item ->
                //iterating all items with their respective indexes
                NavigationBarItem(
                    selected = index == selectedBottomBarItem,
                    label = { Text(text = item.label) },
                    icon = {
                        Icon(
                            item.icon,
                            contentDescription = item.label
                        )
                    },
                    onClick = {
                        selectedBottomBarItem = index
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