package com.example.tfg.model.notifications

import androidx.navigation.NavController
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.common.navHost.ProfileNavigationItems
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class FollowedNotification(
    val user: User,
    private val stringResourcesProvider: StringResourcesProvider,
    private val navController: NavController,
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
        return NotificationButtonInfo(stringResourcesProvider.getString(R.string.notifications_followed_notification_button_text)) {
            val gson: Gson = GsonBuilder().create()
            val user = gson.toJson(user)
            navController.navigate(
                ProfileNavigationItems.ProfileScreen.route.replace(
                    oldValue = "{user}",
                    newValue = user
                )
            )
        }
    }


}