package com.example.tfg.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.tfg.model.Notifications
import com.example.tfg.ui.common.CommonEventHandler
import com.example.tfg.ui.common.navHost.HomeRoutesItems

sealed class NotificationsScreenEvent {

}

data class NotificationsMainState(
    val commonEventHandler: CommonEventHandler,
    val notificationList: ArrayList<Notifications>
)

class NotificationsViewModel(val navController: NavHostController, commonEventHandler: CommonEventHandler) :
    ViewModel() {

    var notificationsMainState by mutableStateOf(NotificationsMainState(commonEventHandler,getUserNotificatiosn()))

    fun onEvent(event: NotificationsScreenEvent) {
//        when (event) {
//        }
    }

    private fun getUserNotificatiosn(): ArrayList<Notifications> {

        return arrayListOf()
    }

}