package com.example.tfg.model

import java.time.LocalDate

interface Activity {

    fun infoForUI() :Int
    fun getDateOfCreation(): LocalDate
    fun relatedBook(): Book
    fun getActivityUser(): User
}