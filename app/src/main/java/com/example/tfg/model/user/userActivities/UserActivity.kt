package com.example.tfg.model.user.userActivities

import com.example.tfg.model.book.Book
import com.example.tfg.model.user.LocalDateSerializer
import com.example.tfg.model.user.User
import kotlinx.serialization.Serializable
import java.time.LocalDate

sealed class Activity {
    abstract val user: User

    @Serializable(LocalDateSerializer::class)
    abstract val creationDate: LocalDate
    abstract val book: Book
    abstract val rating: Int
    abstract val timeStamp: String

    abstract fun infoForUI(): Int
    abstract fun extraInfo(): String
}
