package com.example.tfg.model

import android.os.Parcelable
import com.example.tfg.model.user.LocalDateSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Parcelize
@Serializable
class Book(
    val tittle: String,
    val author: String,
    val coverImage: Int,
    @Serializable(LocalDateSerializer::class) val publicationDate: LocalDate = LocalDate.MIN,
    val pages: Int = 0,
    val meanScore: Double = -1.0,
    val userScore: Int = -1,
    val subjects: List<String> = arrayListOf(),
    val details: String = "",
    val readingState: String = ""
) : Parcelable