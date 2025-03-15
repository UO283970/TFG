package com.example.tfg.ui.friends

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userFollowStates.UserFollowStateInstanceCreator
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import com.example.tfg.ui.common.navHost.ProfileNavigationItems
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class FriendsMainState(
    var userQuery: String = "",
    var queryResult: ArrayList<User> = arrayListOf(),
    var expandedSearchBar: Boolean = false,
    var followedActivity: ArrayList<Activity>
) : Parcelable

class FriendsViewModel (val navController: NavController) : ViewModel() {
    private var savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

    private val _friendsInfo = MutableStateFlow(
        savedStateHandle?.get<FriendsMainState>("friendsInfo") ?: FriendsMainState(
            followedActivity = getFollowedActivity()
        )
    )
    val friendsInfo: StateFlow<FriendsMainState> = _friendsInfo

    fun changeExpandedSearchBar(change: Boolean) {
        _friendsInfo.update {
            val newState = it.copy(expandedSearchBar = change,queryResult = arrayListOf(), userQuery = "")
            savedStateHandle?.set("friendsInfo", newState)
            newState
        }
    }

    fun onlyChangeExpandedSearchBar(change: Boolean) {
        _friendsInfo.update {
            val newState = it.copy(expandedSearchBar = change)
            savedStateHandle?.set("friendsInfo", newState)
            newState
        }
    }

    fun userFriendQueryChange(userQuery: String) {
        _friendsInfo.value = _friendsInfo.value.copy(userQuery = userQuery)
    }

    fun navigateToUserProfile(user: User) {
        savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
        val gson: Gson =
            GsonBuilder().registerTypeAdapter(UserFollowStateEnum::class.java, UserFollowStateInstanceCreator())
                .create()
        val newState = _friendsInfo.value.copy()
        savedStateHandle?.set("friendsInfo", newState)

        val userJson = gson.toJson(user)
        navController.navigate(
            ProfileNavigationItems.ProfileScreen.route.replace(
                oldValue = "{user}",
                newValue = userJson
            )
        )
    }

    fun searchUsers() {
        val foundUsers: ArrayList<User> = arrayListOf()
        /*TODO: Buscar los usuarios en la base de datos*/
        foundUsers.add(
            User(
                "Nombre de Usuario1 sdfnshlñk sdfh sñljhf dfsdhf",
                R.drawable.prueba,
                UserPrivacyLevel.PUBLIC,
                UserFollowStateEnum.FOLLOW
            )
        )
        foundUsers.add(
            User(
                "Nombre de Usuario7",
                R.drawable.prueba,
                UserPrivacyLevel.PUBLIC,
                UserFollowStateEnum.FOLLOWED
            )
        )
        foundUsers.add(
            User(
                "Nombre de Usuario8",
                R.drawable.prueba,
                UserPrivacyLevel.PRIVATE,
                UserFollowStateEnum.REQUESTED
            )
        )

        _friendsInfo.value = _friendsInfo.value.copy(queryResult = foundUsers)
    }

    private fun getFollowedActivity(): ArrayList<Activity> {
        val followedActivity: ArrayList<Activity> = arrayListOf()
        //TODO: Obtener la actividad de las personas a las que sigue el usuario registrado
        val libroTest = Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba)
        val userForTesting =
            User("Nombre de Usuario", R.drawable.prueba, UserPrivacyLevel.PUBLIC, UserFollowStateEnum.FOLLOWED)
        val reviewForTest = ReviewActivity(
            userForTesting,
            LocalDate.now(),
            libroTest,
            "Muy guapo el libro"
        )

        followedActivity.add(reviewForTest)
        return followedActivity
    }
}