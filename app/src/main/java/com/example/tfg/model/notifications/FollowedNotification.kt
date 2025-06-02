package com.example.tfg.model.notifications

import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.common.navHost.ProfileNavigationItems

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
        }
    }

    override fun getRowOnClickRoute(): String {
        return ProfileNavigationItems.OthersProfileScreen.route.replace(
            oldValue = "{userId}",
            newValue = user.userId
        )
    }


}