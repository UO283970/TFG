package com.example.tfg.model

import android.os.Parcelable
import com.example.tfg.model.user.LocalDateSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Parcelize
class Book(
    val tittle: String, val author: String, var coverImage: Int, @Serializable(LocalDateSerializer::class) var publicationDate: LocalDate = LocalDate.MIN, val pages: Int = 0,
    val meanScore: Double = -1.0, val userScore: Int = -1, var subjects: List<String> = arrayListOf(),
    var details: String = ""
) : Parcelable