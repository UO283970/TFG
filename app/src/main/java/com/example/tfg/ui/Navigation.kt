package com.example.tfg.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.example.tfg.ui.friends.friendsScreen
import com.example.tfg.ui.home.homeScreen
import com.example.tfg.ui.lists.ListNavigationItems
import com.example.tfg.ui.lists.listDetails.ListDetails
import com.example.tfg.ui.lists.listScreen
import com.example.tfg.ui.profile.profileScreen
import com.example.tfg.ui.search.searchScreen
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class TopLevelRoute(val route: String, @Contextual val icon: ImageVector)

object Routes {
    val Home = TopLevelRoute("home", Icons.Filled.Home)
    val SearchScreen = TopLevelRoute("search", Icons.Filled.Search)
    val FriendsScreen = TopLevelRoute("friends", Icons.Filled.Face)
    val ListsScreen = TopLevelRoute("lists", Icons.Filled.Email)
    val Profile = TopLevelRoute("profile", Icons.Filled.AccountCircle)
}

@Composable
fun mainAppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            homeScreen(navController)
        }
        composable(Routes.SearchScreen.route) {
            searchScreen()
        }
        composable(Routes.FriendsScreen.route) {
            friendsScreen()
        }
        loginGraph(navController)
        composable(Routes.Profile.route) {
            profileScreen()
        }
    }
}

fun NavGraphBuilder.loginGraph(navController: NavHostController) {
    navigation(startDestination = ListNavigationItems.ListsScreen.route, route = Routes.ListsScreen.route) {
        composable(ListNavigationItems.ListsScreen.route) {
            listScreen(navController)
        }
        composable(ListNavigationItems.ListDetails.route + "/{tittle}") {
            val tittle: String? = navController.currentBackStackEntry?.arguments?.getString("tittle")
            ListDetails(navController, tittle)
        }
    }
}

@Composable
fun MyComponents(navController: NavHostController) {
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
            mainAppNavigation(navController)
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