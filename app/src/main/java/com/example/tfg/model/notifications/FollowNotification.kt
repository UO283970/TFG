package com.example.tfg.model.notifications

import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.common.navHost.ProfileNavigationItems

class FollowNotification(
    val user: User,notificationId: String,timeStamp: String, notificationType: NotificationTypes
) :
    Notification(user.profilePicture, notificationId, timeStamp,notificationType) {
    override fun getNotificationImage(): String {
        return user.profilePicture
    }

    override fun getMainNotificationInfoResource(): Int {
        return R.string.notifications_follow_notification_text
    }

    override fun getExtraInfo(): String {
        return user.userAlias
    }

    override fun getRowOnClickRoute(): String {
        return ProfileNavigationItems.OthersProfileScreen.route.replace(
            oldValue = "{userId}",
            newValue = user.userId
        )
    }


}