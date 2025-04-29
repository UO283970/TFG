package com.example.tfg.model.user.userActivities

import com.example.tfg.R
import com.example.tfg.model.book.Book
import com.example.tfg.model.user.LocalDateSerializer
import com.example.tfg.model.user.User
import kotlinx.serialization.Serializable
import java.time.LocalDate

data class RatingActivity(
    override val user: User,
    @Serializable(LocalDateSerializer::class)
    override val creationDate: LocalDate,
    override val book: Book,
    override val rating: Int,
    override val timeStamp: String
): Activity(){
    override fun infoForUI(): Int {
        return R.string.rating
    }
    override fun extraInfo(): String {
        return ""
    }
}