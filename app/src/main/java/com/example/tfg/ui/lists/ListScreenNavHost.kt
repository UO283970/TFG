package com.example.tfg.ui.lists

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tfg.ui.lists.listDetails.ListDetails


enum class Screen {
    LISTSSCREEN,
    LISTDETAILS
}

sealed class ListNavigationItems(val route: String) {
    object ListsScreen : ListNavigationItems(Screen.LISTSSCREEN.name)
    object ListDetails : ListNavigationItems(Screen.LISTDETAILS.name)
}

@Composable
fun ListNavHost(
    navController: NavHostController,
    startDestination: String = ListNavigationItems.ListsScreen.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ListNavigationItems.ListsScreen.route) {
            listScreen(navController)
        }
        composable(ListNavigationItems.ListDetails.route + "/{tittle}") {
            val tittle: String? = navController.currentBackStackEntry?.arguments?.getString("tittle")
            ListDetails(navController, tittle)
        }
    }
}
