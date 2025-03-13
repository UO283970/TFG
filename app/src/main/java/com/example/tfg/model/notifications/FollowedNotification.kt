package com.example.tfg.model.notifications

import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.common.StringResourcesProvider

class FollowedNotification(
    val user: User,
    private val stringResourcesProvider: StringResourcesProvider,
    notificationImage: Int
) :
    Notification(notificationImage) {
    override fun getNotificationImage(): Int {
        return user.profilePicture
    }

    override fun getMainNotificationInfo(): String {
        return user.userAlias + "\n" + stringResourcesProvider.getString(R.string.notifications_followed_notification_text)
    }

    override fun getButtonInfo(): NotificationButtonInfo {
        return NotificationButtonInfo(stringResourcesProvider.getString(R.string.notifications_followed_notification_button_text)) {}
    }



}