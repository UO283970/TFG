package com.example.tfg.repository.mappers

import com.example.tfg.model.book.Book
import com.example.tfg.model.booklist.DefaultListNames
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.GetAllListInfoQuery
import com.graphQL.GetUserDefaultListQuery.ListOfBook
import com.graphQL.NextPageBooksQuery.NextPageBook
import java.time.LocalDate

fun ListOfBook.toAppBook(): Book {
    var bookPages = 0
    if(this.pages != ""){
        bookPages = Integer.valueOf(this.pages)
    }
    var year = 0
    if(this.publishYear != ""){
        year = Integer.valueOf(this.publishYear)
    }

    return Book(this.title, this.author, this.coverImageURL, LocalDate.ofYearDay(year, 12), bookPages)
}

fun GetAllListInfoQuery.ListOfBook.toAppBook(): Book {
    return Book(this.title, this.author, this.coverImageURL, LocalDate.ofYearDay(Integer.valueOf(this.publishYear), 12), Integer.valueOf(this.pages))
}

fun List<NextPageBook>?.toAppBooksFromPages(stringResourcesProvider: StringResourcesProvider): List<Book>? {
    var listOfBooks = arrayListOf<Book>()

    if(this != null){
        for (list in this){
            listOfBooks.add(list.toAppBookFromPages(stringResourcesProvider))
        }
    }

    return listOfBooks
}

private fun NextPageBook.toAppBookFromPages(stringResourcesProvider: StringResourcesProvider): Book {
    return Book(
        this.title,
        this.author,
        this.coverImageURL,
        pages = if(this.pages.isNotBlank()) Integer.valueOf(this.pages) else 0,
        publicationDate =if(this.publishYear.isNotBlank()) LocalDate.ofYearDay(Integer.valueOf(this.publishYear), 12) else LocalDate.MIN,
        bookId = this.bookId,
        readingState = if(DefaultListNames.valueOf(this.readingState.toString()) != DefaultListNames.NOT_IN_LIST)
            stringResourcesProvider.getString(DefaultListNames.valueOf(this.readingState.toString()).getDefaultListName())
        else "",
        subjects = this.subjects
    )
}
