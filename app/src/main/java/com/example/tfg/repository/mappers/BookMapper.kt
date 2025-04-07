package com.example.tfg.repository.mappers

import com.example.tfg.R
import com.example.tfg.model.Book
import com.graphQL.GetAllListInfoQuery
import com.graphQL.GetUserDefaultListQuery.ListOfBook
import java.time.LocalDate

fun ListOfBook.toAppBook(): Book {
    return Book(this.title, this.author, R.drawable.prueba, LocalDate.ofYearDay(Integer.valueOf(this.publishYear), 12), Integer.valueOf(this.pages))
}

fun GetAllListInfoQuery.ListOfBook.toAppBook(): Book {
    return Book(this.title, this.author, R.drawable.prueba, LocalDate.ofYearDay(Integer.valueOf(this.publishYear), 12), Integer.valueOf(this.pages))
}
