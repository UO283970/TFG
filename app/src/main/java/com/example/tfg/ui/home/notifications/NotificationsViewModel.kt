package com.example.tfg.ui.home.notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.tfg.R
import com.example.tfg.model.notifications.FollowNotification
import com.example.tfg.model.notifications.FollowedNotification
import com.example.tfg.model.notifications.Notification
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userFollowStates.UserFollowStateFollowed
import com.example.tfg.model.user.userPrivacy.UserPrivacyPublic
import com.example.tfg.ui.common.CommonEventHandler
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.common.navHost.HomeRoutesItems

data class NotificationsMainState(
    val notificationList: List<Notification>,
    val friendRequests: List<User> = arrayListOf()
)

class NotificationsViewModel(
    val navController: NavHostController,
    val commonEventHandler: CommonEventHandler,
    private val stringResourcesProvider: StringResourcesProvider
) :
    ViewModel() {
    var notificationsMainState by mutableStateOf(NotificationsMainState(getUserNotifications()))
    fun deleteNotification(notification: Notification) {
        notificationsMainState =
            notificationsMainState.copy(notificationList = notificationsMainState.notificationList - notification)
    }

    fun navigateToFriendRequests() {
        notificationsMainState =
            notificationsMainState.copy(friendRequests = getFriendRequests())
        navController.navigate(HomeRoutesItems.FriendRequestsScreen.route)
    }

    fun deleteFriendRequest(user: User) {
        notificationsMainState =
            notificationsMainState.copy(friendRequests = notificationsMainState.friendRequests - user)
    }

    fun acceptFriendRequest(user: User) {
        val user = user
        addUserToFollowers(user)
        notificationsMainState =
            notificationsMainState.copy(friendRequests = notificationsMainState.friendRequests - user)
    }

    fun profileUser(user: User){
        /*TODO: Navegar a el usuario que clicas */
    }

    private fun getUserNotifications(): ArrayList<Notification> {

        val userForTesting =
            User("Nombre de Usuario", R.drawable.prueba, UserPrivacyPublic(), UserFollowStateFollowed())
        val followNot = FollowNotification(userForTesting, stringResourcesProvider, R.drawable.prueba)
        val followedNoti = FollowedNotification(userForTesting, stringResourcesProvider, R.drawable.prueba)
        return arrayListOf(followNot, followedNoti)
    }


    private fun getFriendRequests(): ArrayList<User> {
        val userForTesting =
            User("Nombre de Usuario", R.drawable.prueba, UserPrivacyPublic(), UserFollowStateFollowed())
        return arrayListOf(userForTesting,userForTesting)
    }


    private fun addUserToFollowers(user: User) {
        /*TODO: Añadir el usuario a tus seguidores*/
    }

}