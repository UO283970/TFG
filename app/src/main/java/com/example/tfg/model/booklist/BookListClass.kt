package com.example.tfg.model.booklist

import com.example.tfg.model.Book
import com.example.tfg.repository.ListRepository
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
class BookListClass(
    var listId: String,
    var listName: String,
    var listImage: Int = 0,
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
}