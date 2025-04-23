package com.example.tfg.ui.common.navHost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.repository.GlobalErrorHandler
import com.example.tfg.ui.bookDetails.BookDetailsScreen
import com.example.tfg.ui.bookDetails.reviews.ReviewsScreen
import com.example.tfg.ui.bookDetails.reviews.creation.ReviewCreation
import com.example.tfg.ui.friends.FriendsScreen
import com.example.tfg.ui.home.HomeScreen
import com.example.tfg.ui.home.notifications.FriendsRequestScreen
import com.example.tfg.ui.home.notifications.NotificationScreen
import com.example.tfg.ui.lists.ListScreen
import com.example.tfg.ui.lists.listCreation.NewListCreationScreen
import com.example.tfg.ui.lists.listDetails.ListDetailsScreen
import com.example.tfg.ui.lists.listModify.ListModifyScreen
import com.example.tfg.ui.profile.ProfileScreen
import com.example.tfg.ui.profile.components.configuration.ConfigurationScreen
import com.example.tfg.ui.profile.components.editScreen.EditScreen
import com.example.tfg.ui.profile.components.statistics.followers.FollowersScreen
import com.example.tfg.ui.profile.components.statistics.follows.FollowsScreen
import com.example.tfg.ui.profile.components.statistics.reviews.OnlyReviews
import com.example.tfg.ui.profile.othersProfile.OthersProfileScreen
import com.example.tfg.ui.search.SearchScreen
import com.example.tfg.ui.userIdentification.LoginScreen
import com.example.tfg.ui.userIdentification.RegisterImageSelector
import com.example.tfg.ui.userIdentification.RegisterScreen
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
    object RegisterImageSelectorScreen : HomeRoutesItems("registerImageSelector")
    object NotificationScreen : HomeRoutesItems("notifications")
    object FriendRequestsScreen : HomeRoutesItems("requests")
}

sealed class ListNavigationItems(val route: String) {
    object ListsScreen : ListNavigationItems("listScreen/{userId}")
    object ListDetails : ListNavigationItems("listDetails/{bookList}")
    object ListModify : ListNavigationItems("listModify")
    object ListCreation : ListNavigationItems("listCreation")
}

sealed class ProfileNavigationItems(val route: String) {
    object ProfileScreen : ProfileNavigationItems("profileScreen/{user}")
    object OthersProfileScreen : ProfileNavigationItems("othersProfileScreen/{userId}")
    object UserReviews : ProfileNavigationItems("userReviews/{id}")
    object UserFollowers : ProfileNavigationItems("userFollowers/{id}")
    object UserFollows : ProfileNavigationItems("userFollows/{id}")
    object EditProfile : ProfileNavigationItems("editProfile")
    object ProfileConfiguration : ProfileNavigationItems("configureProfile")
}
sealed class BookNavigationItems(val route: String) {
    object BookStartGraph : BookNavigationItems("books")
    object BookScreen : BookNavigationItems("bookScreen")
    object ReviewScreen : BookNavigationItems("reviewScreen")
    object ReviewCreationScreen : BookNavigationItems("reviewCreationScreen")
}

@Composable
fun MainAppNavigation(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    GlobalErrorHandler.onAuthError = {
        navController.navigate(HomeRoutesItems.LoginScreen.route) {
            popUpTo(0)
        }
    }

    NavHost(
        navController = navController,
        startDestination = HomeRoutesItems.HomeNav.route
    ) {
        homeGraph(navController, bottomBarState)
        composable(Routes.SearchScreen.route) {
            bottomBarState.value = true
            SearchScreen({ navigateToRoute(it, navController) })
        }
        composable(Routes.FriendsScreen.route) {
            bottomBarState.value = true
            FriendsScreen({ user: User -> navigateToProfileWithUser(user, navController) })
        }
        listsGraph(navController)
        profileGraph(navController, bottomBarState)
        bookGraph(navController, bottomBarState)
    }
}

private fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    navigation(startDestination = HomeRoutesItems.LoginScreen.route, route = HomeRoutesItems.HomeNav.route) {
        composable(HomeRoutesItems.LoginScreen.route) {
            bottomBarState.value = false
            LoginScreen({ navigateToRoute(it, navController) }, { navigateToRouteWithoutSave(it, navController) })
        }
        composable(HomeRoutesItems.RegisterScreen.route) {
            bottomBarState.value = false
            RegisterScreen({ navigateToRoute(it, navController) })
        }
        composable(HomeRoutesItems.RegisterImageSelectorScreen.route) {
            bottomBarState.value = false
            RegisterImageSelector({ navigateToRouteWithoutSave(it, navController) })
        }
        composable(Routes.Home.route) {
            bottomBarState.value = true
            HomeScreen({ navigateToRoute(it, navController) })
        }

        composable(HomeRoutesItems.NotificationScreen.route) {
            bottomBarState.value = false
            NotificationScreen({ navigateToRoute(it, navController) }, { returnToLastScreen(navController) })
        }
        composable(HomeRoutesItems.FriendRequestsScreen.route) {
            bottomBarState.value = false
            FriendsRequestScreen(
                { returnToLastScreen(navController) },
                { user: User -> navigateToProfileWithUser(user, navController) })
        }

    }
}

private fun NavGraphBuilder.listsGraph(
    navController: NavHostController
) {
    navigation(startDestination = ListNavigationItems.ListsScreen.route, route = Routes.ListsScreen.route) {
        composable(ListNavigationItems.ListsScreen.route) {
            ListScreen({ navigateToRoute(it, navController) })
        }
        composable(ListNavigationItems.ListDetails.route) {
            ListDetailsScreen({ returnToLastScreen(navController) }, {navigateToRoute(it,navController)})
        }
        composable(ListNavigationItems.ListModify.route) {
            ListModifyScreen({ returnToLastScreen(navController) })
        }
        composable(ListNavigationItems.ListCreation.route) {
            NewListCreationScreen({ returnToLastScreen(navController) })
        }
    }
}

private fun NavGraphBuilder.profileGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    val navigateSimplified: (String, String) -> Unit = { route, id ->
        navigateToRouteWithId(route, navController, id)
    }


    navigation(startDestination = ProfileNavigationItems.ProfileScreen.route, route = Routes.Profile.route) {
        composable(ProfileNavigationItems.ProfileScreen.route) {
            bottomBarState.value = true
            ProfileScreen({ navigateToRoute(it, navController) }, navigateSimplified)
        }
        composable(ProfileNavigationItems.OthersProfileScreen.route) {
            bottomBarState.value = false
            OthersProfileScreen({ navigateToRoute(it, navController) }, { returnToLastScreen(navController) }, navigateSimplified)
        }
        composable(ProfileNavigationItems.UserReviews.route) {
            OnlyReviews({ returnToLastScreen(navController) })
        }
        composable(ProfileNavigationItems.EditProfile.route) {
            bottomBarState.value = false
            EditScreen({ returnToLastScreen(navController) })
        }
        composable(ProfileNavigationItems.UserFollowers.route) {
            bottomBarState.value = false
            FollowersScreen({ returnToLastScreen(navController) },{navigateToRoute(it,navController)},
                { user: User -> navigateToProfileWithUser(user, navController) })
        }
        composable(ProfileNavigationItems.UserFollows.route) {
            bottomBarState.value = false
            FollowsScreen({ returnToLastScreen(navController) },
                { user: User -> navigateToProfileWithUser(user, navController) })
        }
        composable(ProfileNavigationItems.ProfileConfiguration.route) {
            bottomBarState.value = false
            ConfigurationScreen({ returnToLastScreen(navController) },
                { navigateToRoute(it, navController)})
        }
    }
}

private fun NavGraphBuilder.bookGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    navigation(startDestination = BookNavigationItems.BookScreen.route, route = BookNavigationItems.BookStartGraph.route) {
        composable(BookNavigationItems.BookScreen.route){
            bottomBarState.value = false
            BookDetailsScreen({ navigateToRoute(it, navController) })
        }
        composable(BookNavigationItems.ReviewScreen.route){
            bottomBarState.value = false
            ReviewsScreen({returnToLastScreen(navController)},{ navigateToRoute(it, navController) })
        }
        composable(BookNavigationItems.ReviewCreationScreen.route){
            bottomBarState.value = false
            ReviewCreation({returnToLastScreen(navController)}, {navigateToRouteCleanRoute(it,navController)})
        }
    }
}


private fun navigateToProfileWithUser(user: User, navController: NavHostController) {
    navController.navigate(
        ProfileNavigationItems.OthersProfileScreen.route.replace(
            oldValue = "{userId}",
            newValue = user.userId
        )
    )
}

private fun navigateToRoute(route: String, navController: NavHostController) {
    navController.navigate(route)
}

private fun navigateToRouteCleanRoute(route: String, navController: NavHostController) {
    val previousRoute = navController.currentBackStackEntry?.destination?.route
    if (previousRoute != null) {
        navController.navigate(route){
            popUpTo(previousRoute) {
                inclusive = true
            }
        }
    }else{
        navController.navigate(route)
    }
}

private fun navigateToRouteWithId(route: String, navController: NavHostController, id: String ) {
    navController.navigate(route.replace(oldValue = "{id}", newValue = id))
}

private fun navigateToRouteWithoutSave(route: String, navController: NavHostController) {
    navController.navigate(route) {
        popUpTo(0)
    }
}

private fun returnToLastScreen(navController: NavHostController) {
    navController.popBackStack()
}