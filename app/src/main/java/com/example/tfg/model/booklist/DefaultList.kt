package com.example.tfg.model.booklist

import com.example.tfg.model.book.Book
import com.example.tfg.repository.ListRepository
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class DefaultList(
    var listId: String,
    var listName: String,
    var listImage: String = "",
    var books: ArrayList<Book> = arrayListOf(),
    var listDescription: String = "",
    var numberOfBooks: Int = 0,
    var userId: String = ""
) : BookList {
    override suspend fun getAllListInfo(listRepository: ListRepository): DefaultList? {
        return listRepository.getUserDefaultList(listId,userId)
    }

    override fun getListOfBooks(): ArrayList<Book> {
        return this.books
    }

    override fun getDescription(): String {
        return listDescription
    }

    override fun getName(): String {
        return this.listName
    }

    override fun getBookCount(): Int {
        return numberOfBooks
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DefaultList

        return listId == other.listId
    }

    override fun hashCode(): Int {
        return listId.hashCode()
    }

    override fun getId(): String {
        return this.listId
    }


    override fun canModify(): Boolean {
        return false
    }

    override fun getPrivacy(): ListPrivacy {
        return ListPrivacy.PUBLIC
    }


}
