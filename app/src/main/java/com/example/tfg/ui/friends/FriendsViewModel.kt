package com.example.tfg.ui.friends

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import javax.inject.Inject

@Parcelize
data class FriendsMainState(
    var userQuery: String = "",
    var queryResult: ArrayList<User> = arrayListOf(),
    var expandedSearchBar: Boolean = false,
    var followedActivity: ArrayList<Activity>
) : Parcelable

@HiltViewModel
class FriendsViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _friendsInfo = MutableStateFlow(
        savedStateHandle.get<FriendsMainState>("friendsInfo") ?: FriendsMainState(
            followedActivity = getFollowedActivity()
        )
    )
    val friendsInfo: StateFlow<FriendsMainState> = _friendsInfo


    fun changeExpandedSearchBar(change: Boolean) {
        _friendsInfo.update {
            val newState = it.copy(expandedSearchBar = change,queryResult = arrayListOf(), userQuery = "")
            savedStateHandle["friendsInfo"] = newState
            newState
        }
    }

    fun onlyChangeExpandedSearchBar(change: Boolean) {
        _friendsInfo.update {
            val newState = it.copy(expandedSearchBar = change)
            savedStateHandle["friendsInfo"] = newState
            newState
        }
    }

    fun userFriendQueryChange(userQuery: String) {
        _friendsInfo.value = _friendsInfo.value.copy(userQuery = userQuery)
    }

    fun saveState() {
        val newState = _friendsInfo.value.copy()
        savedStateHandle["friendsInfo"] = newState
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