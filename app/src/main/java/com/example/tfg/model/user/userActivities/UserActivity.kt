package com.example.tfg.model.user.userActivities

import android.os.Parcelable
import com.example.tfg.model.Book
import com.example.tfg.model.user.LocalDateSerializer
import com.example.tfg.model.user.User
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Parcelize
sealed class Activity : Parcelable {
    abstract val user: User
    @Serializable(LocalDateSerializer::class)
    abstract val creationDate: LocalDate
    abstract val book: Book
    abstract val rating:Int

    abstract fun infoForUI() :Int
}
