package com.example.tfg.ui.common.navHost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tfg.R
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.user.User
import com.example.tfg.ui.friends.FriendsScreen
import com.example.tfg.ui.home.HomeScreen
import com.example.tfg.ui.home.notifications.FriendsRequestScreen
import com.example.tfg.ui.home.notifications.NotificationScreen
import com.example.tfg.ui.lists.ListScreen
import com.example.tfg.ui.lists.listCreation.NewListCreationScreen
import com.example.tfg.ui.lists.listDetails.ListDetailsScreen
import com.example.tfg.ui.profile.ProfileScreen
import com.example.tfg.ui.profile.components.OnlyReviews
import com.example.tfg.ui.profile.components.editScreen.EditScreen
import com.example.tfg.ui.profile.othersProfile.OthersProfileScreen
import com.example.tfg.ui.search.SearchScreen
import com.example.tfg.ui.userIdentification.LoginScreen
import com.example.tfg.ui.userIdentification.RegisterScreen
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class TopLevelRoute(val route: String, @Contextual val icon: Int)
object Routes {
    val Home = TopLevelRoute("home", R.drawable.home_icon)
    val SearchScreen = TopLevelRoute("search", R.drawable.search_icon)
    val FriendsScreen = TopLevelRoute("friends", R.drawable.friends_icon)
    val ListsScreen = TopLevelRoute("lists", R.drawable.book_list_icon)
    val Profile = TopLevelRoute("profile", R.drawable.profile_icon)
}

sealed class HomeRoutesItems(val route: String) {
    object HomeNav : HomeRoutesItems("loginGraph")
    object LoginScreen : HomeRoutesItems("login")
    object RegisterScreen : HomeRoutesItems("register")
    object NotificationScreen : HomeRoutesItems("notifications")
    object FriendRequestsScreen : HomeRoutesItems("requests")
}

sealed class ListNavigationItems(val route: String) {
    object ListsScreen : ListNavigationItems("listScreen")
    object ListDetails : ListNavigationItems("listDetails/{bookList}")
    object ListCreation : ListNavigationItems("listCreation")
}

sealed class ProfileNavigationItems(val route: String) {
    object ProfileScreen : ProfileNavigationItems("profileScreen/{user}")
    object OthersProfileScreen : ProfileNavigationItems("othersProfileScreen/{user}")
    object UserReviews : ProfileNavigationItems("userReviews")
    object UserFollowers : ProfileNavigationItems("userFollowers")
    object UserFollows : ProfileNavigationItems("userFollows")
    object EditProfile : ProfileNavigationItems("editProfile")
}

@Composable
fun MainAppNavigation(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoutesItems.HomeNav.route
    ) {
        homeGraph(navController, bottomBarState)
        composable(Routes.SearchScreen.route) {
            bottomBarState.value = true
            SearchScreen()
        }
        composable(Routes.FriendsScreen.route) {
            bottomBarState.value = true
            FriendsScreen({ user: User -> navigateToProfileWithUser(user, navController) })
        }
        listsGraph(navController)
        profileGraph(navController, bottomBarState)
    }
}

private fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
) {
    var startDestination: String = Routes.Home.route
    if (/*TODO: Cuando el usuario no inicio sesión antes*/false) {
        startDestination = HomeRoutesItems.LoginScreen.route
    }

    navigation(startDestination = startDestination, route = HomeRoutesItems.HomeNav.route) {
        composable(HomeRoutesItems.LoginScreen.route) {
            bottomBarState.value = false
            LoginScreen({ navigateToRoute(it, navController) }, { navigateToRouteWithoutSave(it, navController) })
        }
        composable(HomeRoutesItems.RegisterScreen.route) {
            bottomBarState.value = false
            RegisterScreen({ navigateToRoute(it, navController) }, { navigateToRouteWithoutSave(it, navController) })
        }
        composable(Routes.Home.route) {
            bottomBarState.value = true
            HomeScreen({ navigateToRoute(it, navController) })
        }

        composable(HomeRoutesItems.NotificationScreen.route) {
            bottomBarState.value = false
            NotificationScreen({navigateToRoute(it, navController) }, { returnToLastScreen(navController)})
        }
        composable(HomeRoutesItems.FriendRequestsScreen.route) {
            bottomBarState.value = false
            FriendsRequestScreen({ returnToLastScreen(navController) },
                { user: User -> navigateToProfileWithUser(user, navController) })
        }

    }
}

private fun NavGraphBuilder.listsGraph(
    navController: NavHostController
) {
    navigation(startDestination = ListNavigationItems.ListsScreen.route, route = Routes.ListsScreen.route) {
        composable(ListNavigationItems.ListsScreen.route) {
            ListScreen({navigateToRoute(it, navController)}, {navigateToDetailList(navController,it)})
        }
        composable(ListNavigationItems.ListDetails.route) {
            ListDetailsScreen({returnToLastScreen(navController)})
        }
        composable(ListNavigationItems.ListCreation.route) {
            NewListCreationScreen({returnToLastScreen(navController)})
        }
    }
}

private fun NavGraphBuilder.profileGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    navigation(startDestination = ProfileNavigationItems.ProfileScreen.route, route = Routes.Profile.route) {
        composable(ProfileNavigationItems.ProfileScreen.route) {
            bottomBarState.value = true
            ProfileScreen({navigateToRoute(it, navController)}, {returnToLastScreen(navController)})
        }
        composable(ProfileNavigationItems.OthersProfileScreen.route) {
            OthersProfileScreen({navigateToRoute(it, navController)}, {returnToLastScreen(navController)})
        }
        composable(ProfileNavigationItems.UserReviews.route) {
            OnlyReviews({returnToLastScreen(navController)})
        }
        composable(ProfileNavigationItems.EditProfile.route) {
            bottomBarState.value = false
            EditScreen({returnToLastScreen(navController)})
        }
    }
}


private fun navigateToProfileWithUser(user: User, navController: NavHostController) {
    val gson: Gson =
        GsonBuilder().create()
    val userJson = gson.toJson(user)
    navController.navigate(
        ProfileNavigationItems.OthersProfileScreen.route.replace(
            oldValue = "{user}",
            newValue = userJson
        )
    )
}


private fun navigateToDetailList(navController: NavHostController,bookList: BookList) {
    val gson: Gson =GsonBuilder().create()
    val listJson = gson.toJson(bookList)
    navController.navigate(
        ListNavigationItems.ListDetails.route.replace(
            oldValue = "{bookList}",
            newValue = listJson
        )
    )
}

private fun navigateToRoute(route: String, navController: NavHostController) {
    navController.navigate(route)
}

private fun navigateToRouteWithoutSave(route: String, navController: NavHostController) {
    navController.navigate(route) {
        popUpTo(route) { inclusive = true }
    }
}

private fun returnToLastScreen(navController: NavHostController) {
    navController.popBackStack()
}