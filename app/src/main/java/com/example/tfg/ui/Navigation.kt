package com.example.tfg.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.common.navHost.Routes
import com.example.tfg.ui.common.navHost.mainAppNavigation

@Composable
fun navigationBar(navController: NavHostController, stringResourcesProvider: StringResourcesProvider) {
    val items = listOf(
        Routes.Home, Routes.SearchScreen, Routes.FriendsScreen, Routes.ListsScreen, Routes.Profile
    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(

            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(imageVector = screen.icon, contentDescription = null)
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            selectedIndex = index
                            navController.switchTabs(screen.route)
                        }
                    )
                }
            }
        })
    { innerPadding ->
        Box (modifier = Modifier.padding(innerPadding)){
            mainAppNavigation(navController,stringResourcesProvider)
        }
    }
}

fun NavHostController.switchTabs(route: String) {
    navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }

        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true

        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}