package com.example.tfg.model.notifications

import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.common.navHost.ProfileNavigationItems
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class FollowedNotification(
    val user: User, notificationId: String,timeStamp: String, notificationType: NotificationTypes
) :
    Notification(user.profilePicture, notificationId, timeStamp,notificationType) {
    override fun getMainNotificationInfoResource(): Int {
        return R.string.notifications_followed_notification_text
    }

    override fun getExtraInfo(): String {
        return user.userAlias
    }

    override fun getButtonInfo(): NotificationButtonInfo {
        return NotificationButtonInfo(R.string.notifications_followed_notification_button_text) {
            /*TODO: Se envía una solicitud de seguimiento, si es publico se sigue y si no solicitud*/
        }
    }

    override fun getRowOnClickRoute(): String {
        val gson: Gson = GsonBuilder().create()
        val user = gson.toJson(user)
        return ProfileNavigationItems.ProfileScreen.route.replace(
            oldValue = "{user}",
            newValue = user
        )
    }


}