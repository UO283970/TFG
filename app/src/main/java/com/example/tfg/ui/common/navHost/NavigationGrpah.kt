package com.example.tfg.ui.common.navHost

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.repository.GlobalErrorHandler
import com.example.tfg.ui.bookDetails.BookDetailsScreen
import com.example.tfg.ui.bookDetails.reviews.ReviewsScreen
import com.example.tfg.ui.bookDetails.reviews.creation.ReviewCreation
import com.example.tfg.ui.friends.FriendsScreen
import com.example.tfg.ui.home.HomeScreen
import com.example.tfg.ui.home.notifications.NotificationScreen
import com.example.tfg.ui.home.notifications.followRequest.FollowRequestScreen
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
import com.example.tfg.ui.search.components.SearchForEnum
import com.example.tfg.ui.userIdentification.components.ForgetPassScreen
import com.example.tfg.ui.userIdentification.login.LoginScreen
import com.example.tfg.ui.userIdentification.register.RegisterImageSelector
import com.example.tfg.ui.userIdentification.register.RegisterScreen
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
    object PasswordReset : HomeRoutesItems("passwordReset")
    object RegisterScreen : HomeRoutesItems("register")
    object RegisterImageSelectorScreen : HomeRoutesItems("registerImageSelector")
    object NotificationScreen : HomeRoutesItems("notifications")
    object FriendRequestsScreen : HomeRoutesItems("requests")
}

sealed class SearchRoutesItems(val route: String) {
    object SearchMain : SearchRoutesItems("searchMain")
    object SearchWithParameters : SearchRoutesItems("search?userQuery={userQuery}&searchFor={searchFor}")
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

    val navigateToSearchWithParamsSimplified: (String, String) -> Unit = { userQuery, searchFor ->
        navigateToSearchWithParams(navController, userQuery, searchFor)
    }

    NavHost(
        navController = navController,
        startDestination = HomeRoutesItems.HomeNav.route
    ) {
        homeGraph(navController, bottomBarState)
        searchGraph(navController, bottomBarState,navigateToSearchWithParamsSimplified)
        composable(Routes.FriendsScreen.route) {
            bottomBarState.value = true
            FriendsScreen(
                { user: User -> navigateToProfileWithUser(user, navController) },
                { navigateToRoute(it, navController) },
                navigateToSearchWithParamsSimplified
            )
        }
        listsGraph(navController)
        profileGraph(navController, bottomBarState, navigateToSearchWithParamsSimplified)
        bookGraph(navController, bottomBarState,navigateToSearchWithParamsSimplified)
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
        composable(HomeRoutesItems.PasswordReset.route) {
            bottomBarState.value = false
            ForgetPassScreen()
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
            HomeScreen({ navigateToRoute(it, navController) })
            bottomBarState.value = true
        }

        composable(HomeRoutesItems.NotificationScreen.route) {
            bottomBarState.value = false
            NotificationScreen({ navigateToRoute(it, navController) }, { returnToLastScreen(navController) })
        }
        composable(HomeRoutesItems.FriendRequestsScreen.route) {
            bottomBarState.value = false
            FollowRequestScreen(
                { returnToLastScreen(navController) },
                { user: User -> navigateToProfileWithUser(user, navController) })
        }

    }
}

private fun NavGraphBuilder.searchGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    navigateToSearchWithParamsSimplified: (String, String) -> Unit
) {
    navigation(startDestination = SearchRoutesItems.SearchMain.route, route = Routes.SearchScreen.route) {
        composable(route = SearchRoutesItems.SearchMain.route) {
            bottomBarState.value = true
            SearchScreen({ navigateToRoute(it, navController) },navigateToSearchWithParamsSimplified)
        }
        composable(
            route = SearchRoutesItems.SearchWithParameters.route,
            arguments = listOf(
                navArgument("userQuery") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("searchFor") {
                    type = NavType.StringType
                    defaultValue = SearchForEnum.BOOKS.toString()
                })
        ) {
            bottomBarState.value = true
            SearchScreen({ navigateToRoute(it, navController) },navigateToSearchWithParamsSimplified)
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
            ListDetailsScreen({ returnToLastScreen(navController) }, { navigateToRoute(it, navController) })
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
    bottomBarState: MutableState<Boolean>,
    navigateToSearchWithParamsSimplified: (String, String) -> Unit
) {
    val navigateSimplified: (String, String) -> Unit = { route, id ->
        navigateToRouteWithId(route, navController, id)
    }
    navigation(startDestination = ProfileNavigationItems.ProfileScreen.route, route = Routes.Profile.route) {
        composable(ProfileNavigationItems.ProfileScreen.route) {
            bottomBarState.value = true
            ProfileScreen({ navigateToRoute(it, navController) }, navigateSimplified)
        }
        composable(ProfileNavigationItems.UserFollows.route) {
            bottomBarState.value = false
            FollowsScreen(
                { returnToLastScreen(navController) },
                { user: User -> navigateToProfileWithUser(user, navController) })
        }
        composable(ProfileNavigationItems.UserFollowers.route) {
            bottomBarState.value = false
            FollowersScreen(
                { returnToLastScreen(navController) }, { navigateToRoute(it, navController) },
                { user: User -> navigateToProfileWithUser(user, navController) })
        }
        composable(ProfileNavigationItems.UserReviews.route) {
            OnlyReviews(
                { returnToLastScreen(navController) },
                { user: User -> navigateToProfileWithUser(user, navController) },
                { navigateToRoute(it, navController) },
                navigateToSearchWithParamsSimplified
            )
        }
        composable(ProfileNavigationItems.EditProfile.route) {
            bottomBarState.value = false
            EditScreen({ returnToLastScreen(navController) })
        }
        composable(ProfileNavigationItems.OthersProfileScreen.route) {
            bottomBarState.value = false
            OthersProfileScreen({ navigateToRoute(it, navController) }, { returnToLastScreen(navController) }, navigateSimplified)
        }
        composable(ProfileNavigationItems.ProfileConfiguration.route) {
            bottomBarState.value = false
            ConfigurationScreen(
                { returnToLastScreen(navController) },
                { navigateToRoute(it, navController) })
        }
    }
}

private fun NavGraphBuilder.bookGraph(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    navigateToSearchWithParamsSimplified: (String, String) -> Unit
) {
    navigation(startDestination = BookNavigationItems.BookScreen.route, route = BookNavigationItems.BookStartGraph.route) {
        composable(BookNavigationItems.BookScreen.route) {
            bottomBarState.value = false
            BookDetailsScreen({ navigateToRoute(it, navController) }, { returnToLastScreen(navController) }, navigateToSearchWithParamsSimplified)
        }
        composable(BookNavigationItems.ReviewScreen.route) {
            bottomBarState.value = false
            ReviewsScreen({ returnToLastScreen(navController) }, { navigateToRoute(it, navController) })
        }
        composable(BookNavigationItems.ReviewCreationScreen.route) {
            bottomBarState.value = false
            ReviewCreation({ returnToLastScreen(navController) }, { navigateToRouteCleanRoute(it, navController) })
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
    if (previousRoute == route) {
        navController.popBackStack()
    } else if (previousRoute != null) {
        navController.navigate(route) {
            popUpTo(previousRoute) {
                inclusive = true
            }
        }
    } else {
        navController.navigate(route)
    }
}

private fun navigateToRouteWithId(route: String, navController: NavHostController, id: String) {
    navController.navigate(route.replace(oldValue = "{id}", newValue = id))
}

private fun navigateToSearchWithParams(navController: NavHostController, userQuery: String, searchFor: String) {
    navController.navigate(
        SearchRoutesItems.SearchWithParameters.route.replace(oldValue = "{userQuery}", newValue = userQuery)
            .replace(oldValue = "{searchFor}", newValue = searchFor)
    )
}

private fun navigateToRouteWithoutSave(route: String, navController: NavHostController) {
    navController.navigate(route) {
        popUpTo(0)
    }
}

private fun returnToLastScreen(navController: NavHostController) {
    navController.popBackStack()
}