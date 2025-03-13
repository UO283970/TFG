package com.example.tfg.ui.friends

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.user.Activity
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.model.user.userFollowStates.UserFollowStateFollowed
import com.example.tfg.model.user.userFollowStates.UserFollowStatePending
import com.example.tfg.model.user.userFollowStates.UserFollowStateUnfollow
import com.example.tfg.model.user.userPrivacy.UserPrivacyPrivate
import com.example.tfg.model.user.userPrivacy.UserPrivacyPublic
import java.time.LocalDate

data class FriendsMainState(
    var userQuery: String = "",
    var queryResult: ArrayList<User> = arrayListOf(),
    var expandedSearchBar: Boolean = false,
    var followedActivity: ArrayList<Activity> = arrayListOf()
)

class FriendsViewModel : ViewModel() {
    var friendsInfo by mutableStateOf(FriendsMainState().copy(followedActivity = getFollowedActivity()))
    fun changeExpandedSearchBar(change:Boolean) {
        friendsInfo = friendsInfo.copy(expandedSearchBar = change)
    }

    fun userFriendQueryChange(userQuery: String) {
        friendsInfo = friendsInfo.copy(userQuery = userQuery)
    }

    fun searchUsers() {
        val foundUsers: ArrayList<User> = arrayListOf()
        /*TODO: Buscar los usuarios en la base de datos*/
        foundUsers.add(
            User(
                "Nombre de Usuario1 sdfnshlñk sdfh sñljhf dfsdhf",
                R.drawable.prueba,
                UserPrivacyPublic(),
                UserFollowStateUnfollow()
            )
        )
        foundUsers.add(User("Nombre de Usuario2", R.drawable.prueba, UserPrivacyPublic(), UserFollowStateFollowed()))
        foundUsers.add(User("Nombre de Usuario3", R.drawable.prueba, UserPrivacyPrivate(), UserFollowStatePending()))

        friendsInfo = friendsInfo.copy(queryResult = foundUsers)
    }

    private fun getFollowedActivity(): ArrayList<Activity> {
        val followedActivity: ArrayList<Activity> = arrayListOf()
        //TODO: Obtener la actividad de las personas a las que sigue el usuario registrado
        val libroTest = Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba)
        val userForTesting = User("Nombre de Usuario", R.drawable.prueba, UserPrivacyPublic(), UserFollowStateFollowed())
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