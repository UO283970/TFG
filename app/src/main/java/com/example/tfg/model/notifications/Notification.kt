package com.example.tfg.model.notifications

abstract class Notification(val image: Int) {
    abstract fun getNotificationImage(): Int
    abstract fun getMainNotificationInfo(): String
    open fun getButtonInfo(): NotificationButtonInfo?{
        return null
    }
    open fun getRowOnClick(): () -> Unit{
        return {}
    }
}

data class NotificationButtonInfo(val tittle: String,val action: () -> Unit)
