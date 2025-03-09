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
import com.example.tfg.model.BookList
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.friends.FriendsViewModel
import com.example.tfg.ui.friends.friendsScreen
import com.example.tfg.ui.home.HomeViewModel
import com.example.tfg.ui.home.homeScreen
import com.example.tfg.ui.lists.ListViewModel
import com.example.tfg.ui.lists.listDetails.ListDetailsScreen
import com.example.tfg.ui.lists.listDetails.ListDetailsViewModel
import com.example.tfg.ui.lists.listScreen
import com.example.tfg.ui.profile.ProfileViewModel
import com.example.tfg.ui.profile.profileScreen
import com.example.tfg.ui.search.SearchViewModel
import com.example.tfg.ui.search.searchScreen
import com.example.tfg.ui.userIdentification.LoginViewModel
import com.example.tfg.ui.userIdentification.RegisterViewModel
import com.example.tfg.ui.userIdentification.loginScreen
import com.example.tfg.ui.userIdentification.registerScreen
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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

sealed class ListNavigationItems(val route: String) {
    object ListsScreen : ListNavigationItems("listScreen")
    object ListDetails : ListNavigationItems("listDetails/{list}")
}


@Composable
fun mainAppNavigation(navController: NavHostController, stringResourcesProvider: StringResourcesProvider) {
    NavHost(
        navController = navController,
        startDestination = /*LoginRoutesItems.LoginNav.route*/Routes.ListsScreen.route
    ) {
        loginGraph(navController,stringResourcesProvider)
        composable(Routes.SearchScreen.route) {
            searchScreen(SearchViewModel())
        }
        composable(Routes.FriendsScreen.route) {
            friendsScreen(FriendsViewModel())
        }
        listsGraph(navController,stringResourcesProvider)
        composable(Routes.Profile.route) {
            profileScreen(ProfileViewModel(navController,stringResourcesProvider))
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
            loginScreen(LoginViewModel(navController,stringResourcesProvider))
        }
        composable(LoginRoutesItems.RegisterScreen.route) {
            registerScreen(RegisterViewModel(navController,stringResourcesProvider))
        }
        composable(Routes.Home.route) {
            homeScreen(HomeViewModel(navController))
        }

    }
}

fun NavGraphBuilder.listsGraph(navController: NavHostController, stringResourcesProvider: StringResourcesProvider) {
    navigation(startDestination = ListNavigationItems.ListsScreen.route, route = Routes.ListsScreen.route) {
        composable(ListNavigationItems.ListsScreen.route) {
            listScreen(ListViewModel(navController,stringResourcesProvider))
        }
        composable(ListNavigationItems.ListDetails.route) {
            val gson: Gson = GsonBuilder().create()
            val bookListJson: String? = navController.currentBackStackEntry?.arguments?.getString("list")
            val bookList = gson.fromJson(bookListJson, BookList::class.java)
            if(bookList != null){
                ListDetailsScreen(ListDetailsViewModel(navController,bookList))
            }
        }
    }
}