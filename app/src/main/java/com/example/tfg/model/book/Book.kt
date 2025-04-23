package com.example.tfg.model.book

import com.example.tfg.model.user.LocalDateSerializer
import com.example.tfg.model.user.userActivities.ReviewActivity
import kotlinx.serialization.Serializable
import java.time.LocalDate

class Book(
    val tittle: String = "",
    val author: String = "",
    val coverImage: String = "",
    @Serializable(LocalDateSerializer::class) val publicationDate: LocalDate = LocalDate.MIN,
    val pages: Int = 0,
    var meanScore: Double = 0.0,
    var userScore: Int = 0,
    val subjects: List<String> = arrayListOf(),
    val details: String = "",
    var readingState: String = "",
    val bookId: String = "",
    var userProgression: Int = -1,
    var numberOfReviews: Int = 0,
    var totalRatings: Int = 0,
    var listOfUserProfilePicturesForReviews: ArrayList<String> = arrayListOf<String>(),
   var listOfReviews: ArrayList<ReviewActivity> = arrayListOf<ReviewActivity>()
)