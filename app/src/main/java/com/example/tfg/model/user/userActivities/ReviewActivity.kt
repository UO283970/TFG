package com.example.tfg.model.user.userActivities

import com.example.tfg.R
import com.example.tfg.model.book.Book
import com.example.tfg.model.user.LocalDateSerializer
import com.example.tfg.model.user.User
import kotlinx.serialization.Serializable
import java.time.LocalDate

data class ReviewActivity(
    override val user: User,
    @Serializable(LocalDateSerializer::class)
    override val creationDate: LocalDate,
    override val book: Book,
    val reviewText: String,
    override val rating: Int = -1
): Activity(){
    override fun infoForUI(): Int {
        return R.string.has_reviewed
    }

    override fun extraInfo(): String {
        return reviewText
    }


}