package com.example.tfg.model.notifications

abstract class Notification(val image: Int) {
    abstract fun getMainNotificationInfoResource(): Int
    abstract fun getExtraInfo(): String
    abstract fun getRowOnClickRoute(): String

    open fun getNotificationImage(): Int{
        return image
    }
    open fun getButtonInfo(): NotificationButtonInfo?{
        return null
    }
}

data class NotificationButtonInfo(val tittleResource: Int,val action: () -> Unit)
