package com.example.tfg.model

import java.time.LocalDate

class Book(
    tittle: String, author: String, var coverImage: Int, var publicationDate: LocalDate = LocalDate.MIN, pages: Int = 0,
    meanScore: Double = -1.0, userScore: Int = -1, var subjects: List<String> = arrayListOf(),
    var details: String = ""
) {
    var tittle: String = tittle
        private set
    var author: String = author
        private set
    var pages: Int = if (pages >= 0) pages else 0
        set(value) {
            field = if (value >= 0) value else 0
        }
    var meanScore: Double = if (meanScore in 0.0..10.0) meanScore else -1.0
        set(value) {
            field = if (value >= -1.0 && value <= 10.0) value else -1.0
        }
    var userScore: Int = if (userScore in 0..10) userScore else -1
        set(value) {
            field = if (value >= -1 && value <= 10) value else -1
        }
}