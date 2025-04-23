package com.example.tfg.model.booklist

import com.example.tfg.model.book.Book
import com.example.tfg.repository.ListRepository

class BookListClass(
    var listId: String,
    var listName: String,
    var listImage: String = "",
    var books: ArrayList<Book> = arrayListOf(),
    var listDescription: String = "",
    var numberOfBooks: Int = 0,
    var listPrivacy: ListPrivacy = ListPrivacy.PUBLIC,
    var userId: String = ""
) :
    BookList {
    override suspend fun getAllListInfo(listRepository: ListRepository): BookListClass? {
        return listRepository.getAllListInfo(listId)
    }

    override fun getListOfBooks(): ArrayList<Book> {
        return books
    }

    override fun getDescription(): String {
        return listDescription
    }

    override fun getName(): String {
        return listName
    }

    override fun getBookCount(): Int {
        return numberOfBooks
    }

    override fun getId(): String {
        return this.listId
    }

    override fun canModify(): Boolean {
        return true
    }

    override fun getPrivacy(): ListPrivacy {
        return this.listPrivacy
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookListClass

        return listId == other.listId
    }

    override fun hashCode(): Int {
        return listId.hashCode()
    }


}