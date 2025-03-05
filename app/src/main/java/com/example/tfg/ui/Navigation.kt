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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tfg.ui.friends.friendsScreen
import com.example.tfg.ui.home.homeScreen
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
        composable(Routes.ListsScreen.route) {
            listScreen(navController)
        }
        composable(Routes.Profile.route) {
            profileScreen()
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
                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(imageVector = screen.icon, contentDescription = null)
                        },
                        selected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    //saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true

                            }
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