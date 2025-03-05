package com.example.tfg.model

import java.time.LocalDate

class Review(
    private var userName: String, private var creationDate: LocalDate,
    private var book: Book, private var reviewText: String, private var rating: Int = -1
)