package com.example.tfg.model.notifications

import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.common.StringResourcesProvider

class FollowNotification(
    val user: User,
    private val stringResourcesProvider: StringResourcesProvider,
    notificationImage: Int
) :
    Notification(notificationImage) {
    override fun getNotificationImage(): Int {
        return user.profilePicture
    }

    override fun getMainNotificationInfo(): String {
        return stringResourcesProvider.getString(R.string.notifications_follow_notification_text) + "\n" + user.userAlias
    }


}