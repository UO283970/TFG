package com.example.tfg.ui.home.notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.notifications.FollowNotification
import com.example.tfg.model.notifications.FollowedNotification
import com.example.tfg.model.notifications.Notification
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class NotificationsMainState(
    val notificationList: List<Notification>,
    val friendRequests: List<User> = arrayListOf()
)

@HiltViewModel
class NotificationsViewModel @Inject constructor() :
    ViewModel() {
    var notificationsMainState by mutableStateOf(NotificationsMainState(getUserNotifications()))
    fun deleteNotification(notification: Notification) {
        notificationsMainState =
            notificationsMainState.copy(notificationList = notificationsMainState.notificationList - notification)
    }

    fun obtainFriendRequests() {
        notificationsMainState =
            notificationsMainState.copy(friendRequests = getFriendRequests())
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

    private fun getUserNotifications(): ArrayList<Notification> {

        val userForTesting =
            User("Nombre de Usuario", R.drawable.prueba, UserPrivacyLevel.PUBLIC, UserFollowStateEnum.FOLLOWED)
        val followNot = FollowNotification(userForTesting)
        val followedNoti = FollowedNotification(userForTesting)
        return arrayListOf(followNot, followedNoti)
    }


    private fun getFriendRequests(): ArrayList<User> {
        val userForTesting =
            User("Nombre de Usuario", R.drawable.prueba, UserPrivacyLevel.PUBLIC, UserFollowStateEnum.FOLLOWED)
        return arrayListOf(userForTesting,userForTesting)
    }


    private fun addUserToFollowers(user: User) {
        /*TODO: Añadir el usuario a tus seguidores*/
    }

}