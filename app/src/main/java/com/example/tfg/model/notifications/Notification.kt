package com.example.tfg.model.notifications

abstract class Notification(val image: String, val notificationId: String, val timeStamp: String, val notificationTypes: NotificationTypes) {
    abstract fun getMainNotificationInfoResource(): Int
    abstract fun getExtraInfo(): String
    abstract fun getRowOnClickRoute(): String

    open fun getNotificationImage(): String{
        return image
    }
    open fun getButtonInfo(): NotificationButtonInfo?{
        return null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Notification

        return notificationId == other.notificationId
    }

    override fun hashCode(): Int {
        return notificationId.hashCode()
    }


}

data class NotificationButtonInfo(val tittleResource: Int,val action: () -> Unit)
