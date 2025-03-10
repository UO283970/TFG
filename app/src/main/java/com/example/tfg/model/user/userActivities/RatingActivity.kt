package com.example.tfg.model.user.userActivities

import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.user.Activity
import com.example.tfg.model.user.User
import java.time.LocalDate

class RatingActivity(
    var user: User, var creationDate: LocalDate,
    var book: Book, var rating: Int = -1
): Activity {
    override fun infoForUI(): Int{
        return R.string.rating_text_info_with_valoration
    }

    override fun getDateOfCreation(): LocalDate {
        return creationDate
    }

    override fun relatedBook(): Book {
        return book
    }

    override fun getActivityUser(): User {
        return user
    }
}