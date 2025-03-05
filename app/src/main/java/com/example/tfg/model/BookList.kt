package com.example.tfg.model

class BookList(var listName: String, books: Array<Book> = emptyArray(), var listDescription : String = ""){
    val books: Array<Book>
        get() = books.clone()

    fun _getBooks(): Array<Book> {
        return books
    }
}