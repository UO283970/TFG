package com.example.tfg.ui.home.notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.notifications.Notification
import com.example.tfg.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationsMainState(
    val notificationList: MutableList<Notification> = mutableListOf<Notification>(),
    val changeNotifications: Boolean = false,
    val loadingNotifications: Boolean = true,
    val canGetMoreInfo: Boolean = false,
    val isRefreshing: Boolean = false,
)

@HiltViewModel
class NotificationsViewModel @Inject constructor(val userRepository: UserRepository) :
    ViewModel() {
    var notificationsMainState by mutableStateOf(NotificationsMainState())

    init {
        getUserNotifications()
    }

    fun deleteNotification(notification: Notification) {
        viewModelScope.launch {
            val notificationList = userRepository.deleteNotification(notificationId = notification.notificationId);
            if(notificationList != null){
                notificationsMainState.notificationList.remove(notification)
                notificationsMainState =
                    notificationsMainState.copy(changeNotifications = !notificationsMainState.changeNotifications)
            }
        }

    }

    fun getUserNotifications() {
        viewModelScope.launch {
            val timeStamp =
                if (notificationsMainState.notificationList.isNotEmpty())
                    notificationsMainState.notificationList[notificationsMainState.notificationList.size - 1].timeStamp
                else ""

            val notificationList = userRepository.getUserNotifications(timeStamp = timeStamp);
            if(notificationList != null){
                notificationsMainState.notificationList.addAll(notificationList)
                notificationsMainState = notificationsMainState.copy(loadingNotifications = false)
                notificationsMainState = notificationsMainState.copy(isRefreshing = false)
                notificationsMainState = notificationsMainState.copy(changeNotifications = !notificationsMainState.changeNotifications)
            }
        }
    }

    fun refreshNotifications(){
        notificationsMainState = notificationsMainState.copy(isRefreshing = true)
        getUserNotifications()
    }

    fun deleteAllNotifications(){
        viewModelScope.launch {
            val notificationList = userRepository.deleteAllNotification();
            if (notificationList != null) {
                notificationsMainState.notificationList.clear()
                notificationsMainState =
                    notificationsMainState.copy(changeNotifications = !notificationsMainState.changeNotifications)
            }
        }
    }

}