package com.example.tfg.model

import com.example.tfg.R

enum class ConfigurationItems(val stringResource: Int, val mainIcon: Int, val route: String) {
    SECURITY(R.string.profile_configuration_security, R.drawable.security_icon, ""),
    NOTIFICATIONS(R.string.profile_configuration_notifications, R.drawable.notification_icon, ""),
    DELETE_ACCOUNT(R.string.profile_configuration_delete_account, R.drawable.delete_icon, ""),
    LOGOUT(R.string.profile_configuration_logout, R.drawable.logout_icon,"");

}