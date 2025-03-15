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
import com.example.tfg.model.user.UserFollowState
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userFollowStates.UserFollowStateInstanceCreator
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import com.example.tfg.ui.common.CommonEventHandler
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.friends.friendsScreen
import com.example.tfg.ui.home.HomeViewModel
import com.example.tfg.ui.home.homeScreen
import com.example.tfg.ui.home.notifications.NotificationsViewModel
import com.example.tfg.ui.home.notifications.friendsRequestScreen
import com.example.tfg.ui.home.notifications.notificationScreen
import com.example.tfg.ui.lists.ListCreationViewModel
import com.example.tfg.ui.lists.ListViewModel
import com.example.tfg.ui.lists.listDetails.ListDetailsScreen
import com.example.tfg.ui.lists.listDetails.ListDetailsViewModel
import com.example.tfg.ui.lists.listScreen
import com.example.tfg.ui.lists.newListCreationScreen
import com.example.tfg.ui.profile.ProfileViewModel
import com.example.tfg.ui.profile.components.editScreen.EditScreen
import com.example.tfg.ui.profile.components.onlyReviews
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
    object HomeScreen : HomeRoutesItems("login")
    object RegisterScreen : HomeRoutesItems("register")
    object NotificationScreen : HomeRoutesItems("notifications")
    object FriendRequestsScreen : HomeRoutesItems("requests")
}

sealed class ListNavigationItems(val route: String) {
    object ListsScreen : ListNavigationItems("listScreen")
    object ListDetails : ListNavigationItems("listDetails/{list}")
    object ListCreation : ListNavigationItems("listCreation")
}

sealed class ProfileNavigationItems(val route: String) {
    object ProfileScreen : ProfileNavigationItems("profileScreen/{user}")
    object UserReviews : ProfileNavigationItems("userReviews")
    object EditProfile : ProfileNavigationItems("editProfile")
}

@Composable
fun mainAppNavigation(
    navController: NavHostController,
    stringResourcesProvider: StringResourcesProvider,
    bottomBarState: MutableState<Boolean>,
    commonEventHandler: CommonEventHandler
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ListsScreen.route
    ) {
        homeGraph(navController, stringResourcesProvider, bottomBarState, commonEventHandler)
        composable(Routes.SearchScreen.route) {
            searchScreen(SearchViewModel())
        }

        composable(Routes.FriendsScreen.route) {
            friendsScreen({ user: User, _: String -> navigateToProfileWithUser(user,navController)})
        }
        listsGraph(navController, stringResourcesProvider, commonEventHandler)
        val userConnected = User(
            /*TODO: Le pasas el usuario que esta conectado porque se accede desde el menú*/
            "Nombre de Usuario2",
            R.drawable.prueba,
            UserPrivacyLevel.PUBLIC,
            UserFollowStateEnum.OWN,
            userName = "Nombre de Usuario2"
        )
        profileGraph(navController, stringResourcesProvider, commonEventHandler, bottomBarState,userConnected)
    }
}

fun navigateToProfileWithUser(user: User,navController: NavHostController){
    val gson: Gson =
        GsonBuilder().registerTypeAdapter(UserFollowStateEnum::class.java, UserFollowStateInstanceCreator())
            .create()
    val userJson = gson.toJson(user)
    navController.navigate(
        ProfileNavigationItems.ProfileScreen.route.replace(
            oldValue = "{user}",
            newValue = userJson
        )
    )
}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    stringResourcesProvider: StringResourcesProvider,
    bottomBarState: MutableState<Boolean>,
    commonEventHandler: CommonEventHandler
) {
    var startDestination: String = Routes.Home.route
    if (/*TODO: Cuando el usuario no inico sesion antes*/false) {
        startDestination = HomeRoutesItems.HomeScreen.route
    }

    navigation(startDestination = startDestination, route = HomeRoutesItems.HomeNav.route) {
        composable(HomeRoutesItems.HomeScreen.route) {
            bottomBarState.value = false
            loginScreen(LoginViewModel(navController, stringResourcesProvider))
        }
        composable(HomeRoutesItems.RegisterScreen.route) {
            bottomBarState.value = false
            registerScreen(RegisterViewModel(navController, stringResourcesProvider))
        }
        composable(Routes.Home.route) {
            bottomBarState.value = true
            homeScreen(HomeViewModel(navController))
        }

        val viewModel = NotificationsViewModel(navController, commonEventHandler, stringResourcesProvider)
        composable(HomeRoutesItems.NotificationScreen.route) {
            bottomBarState.value = false
            notificationScreen(viewModel)
        }
        composable(HomeRoutesItems.FriendRequestsScreen.route) {
            bottomBarState.value = false
            friendsRequestScreen(viewModel)
        }

    }
}

fun NavGraphBuilder.listsGraph(
    navController: NavHostController,
    stringResourcesProvider: StringResourcesProvider,
    commonEventHandler: CommonEventHandler
) {
    navigation(startDestination = ListNavigationItems.ListsScreen.route, route = Routes.ListsScreen.route) {
        composable(ListNavigationItems.ListsScreen.route) {
            listScreen(ListViewModel(navController, stringResourcesProvider))
        }
        composable(ListNavigationItems.ListDetails.route) {
            val gson: Gson = GsonBuilder().create()
            val bookListJson: String? = navController.currentBackStackEntry?.arguments?.getString("list")
            val bookList = gson.fromJson(bookListJson, BookList::class.java)
            if (bookList != null) {
                ListDetailsScreen(ListDetailsViewModel(bookList, commonEventHandler))
            }
        }
        composable(ListNavigationItems.ListCreation.route) {
            newListCreationScreen(ListCreationViewModel(commonEventHandler, stringResourcesProvider))
        }
    }
}

fun NavGraphBuilder.profileGraph(
    navController: NavHostController,
    stringResourcesProvider: StringResourcesProvider,
    commonEventHandler: CommonEventHandler,
    bottomBarState: MutableState<Boolean>,
    user: User
) {
    navigation(startDestination = ProfileNavigationItems.ProfileScreen.route, route = Routes.Profile.route) {
        var userPassed = user

        var profileViewModel = ProfileViewModel(navController, stringResourcesProvider, commonEventHandler, user)
        composable(ProfileNavigationItems.ProfileScreen.route) {
            val gson: Gson =
                GsonBuilder().registerTypeAdapter(UserFollowState::class.java, UserFollowStateInstanceCreator())
                    .create()
            val userJson: String? = navController.currentBackStackEntry?.arguments?.getString("user")
            if (userJson != null) {
                userPassed = gson.fromJson(userJson, User::class.java)
                bottomBarState.value = false
            }else{
                bottomBarState.value = true
            }

            profileViewModel = ProfileViewModel(navController, stringResourcesProvider, commonEventHandler, userPassed)
            profileScreen(profileViewModel)
        }
        composable(ProfileNavigationItems.UserReviews.route) {
            onlyReviews(commonEventHandler, profileViewModel)
        }
        composable(ProfileNavigationItems.EditProfile.route) {
            bottomBarState.value = false
            EditScreen(profileViewModel)
        }
    }
}