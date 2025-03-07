package com.example.tfg.ui.common.navHost

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tfg.StringResourcesProvider
import com.example.tfg.ui.friends.friendsScreen
import com.example.tfg.ui.home.homeScreen
import com.example.tfg.ui.lists.ListNavigationItems
import com.example.tfg.ui.lists.listDetails.ListDetails
import com.example.tfg.ui.lists.listScreen
import com.example.tfg.ui.login.LoginViewModel
import com.example.tfg.ui.login.loginScreen
import com.example.tfg.ui.login.registerScreen
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

sealed class LoginRoutesItems(val route: String) {
    object LoginNav : LoginRoutesItems("loginGraph")
    object LoginScreen : LoginRoutesItems("login")
    object RegisterScreen : LoginRoutesItems("register")
}


@Composable
fun mainAppNavigation(navController: NavHostController, stringResourcesProvider: StringResourcesProvider) {
    NavHost(
        navController = navController,
        startDestination = LoginRoutesItems.LoginNav.route
    ) {
        loginGraph(navController,stringResourcesProvider)
        composable(Routes.SearchScreen.route) {
            searchScreen()
        }
        composable(Routes.FriendsScreen.route) {
            friendsScreen()
        }
        listsGraph(navController)
        composable(Routes.Profile.route) {
            profileScreen()
        }
    }
}

fun NavGraphBuilder.loginGraph(navController: NavHostController, stringResourcesProvider: StringResourcesProvider) {
    var startDestination :String = Routes.Home.route
    if(/*TODO: Cuando el usuario no inico sesion antes*/true){
        startDestination = LoginRoutesItems.LoginScreen.route
    }

    navigation(startDestination = startDestination, route = LoginRoutesItems.LoginNav.route) {
        composable(LoginRoutesItems.LoginScreen.route) {
            loginScreen(navController,LoginViewModel(navController,stringResourcesProvider))
        }
        composable(LoginRoutesItems.RegisterScreen.route) {
            registerScreen(navController,LoginViewModel(navController,stringResourcesProvider))
        }
        composable(Routes.Home.route) {
            homeScreen(navController)
        }

    }
}

fun NavGraphBuilder.listsGraph(navController: NavHostController) {
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