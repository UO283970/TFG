package com.example.tfg.ui.friends

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.Activity
import com.example.tfg.model.Book
import com.example.tfg.model.User
import com.example.tfg.model.UserFollow
import com.example.tfg.model.UserPrivacy
import com.example.tfg.model.userActivities.ReviewActivity
import java.time.LocalDate

sealed class FriendScreenEvent {
    data class UserFriendQueryChange(val userQuery: String) : FriendScreenEvent()
    data class ChangeExpandedSearchBar(val change:Boolean) : FriendScreenEvent()
    object SearchUsers : FriendScreenEvent()
}

data class FriendsMainState(
    var userQuery: String = "",
    var queryResult: ArrayList<User> = arrayListOf(),
    var expandedSearchBar: Boolean = false,
    var followedActivity:ArrayList<Activity> = arrayListOf()
)

class FriendsViewModel() : ViewModel() {
    var friendsInfo by mutableStateOf(FriendsMainState().copy(followedActivity = getFollowedActivity()))

    fun onEvent(event: FriendScreenEvent){
        when(event){
            is FriendScreenEvent.ChangeExpandedSearchBar -> {
                friendsInfo = friendsInfo.copy(expandedSearchBar = event.change)
            }
            is FriendScreenEvent.UserFriendQueryChange ->{
                friendsInfo = friendsInfo.copy(userQuery = event.userQuery)
            }
            is FriendScreenEvent.SearchUsers ->{
                searchUsers()
            }
        }
    }

    private fun searchUsers() {
        val foundUsers: ArrayList<User> = arrayListOf()
        /*TODO: Buscar los usuarios en la base de datos*/
        foundUsers.add(User("Nombre de Usuario1 sdfnshlñk sdfh sñljhf dfsdhf", R.drawable.prueba, UserFollow.FOLLOW, UserPrivacy.PUBLIC))
        foundUsers.add(User("Nombre de Usuario2", R.drawable.prueba, UserFollow.`NOT-FOLLOW`, UserPrivacy.PUBLIC))
        foundUsers.add(User("Nombre de Usuario3", R.drawable.prueba, UserFollow.REQUESTED, UserPrivacy.PUBLIC))

        friendsInfo = friendsInfo.copy(queryResult = foundUsers)
    }

    private fun getFollowedActivity(): ArrayList<Activity> {
        val followedActivity: ArrayList<Activity> = arrayListOf()
        //TODO: Obtener la actividad de las personas a las que sigue el usuario registrado
        val libroTest = Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba)
        val userForTesting = User("Nombre de Usuario", R.drawable.prueba, UserFollow.FOLLOW, UserPrivacy.PUBLIC)
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