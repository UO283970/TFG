package com.example.tfg.model.booklist

import com.example.tfg.model.book.Book
import com.example.tfg.repository.ListRepository

interface BookList{

    suspend fun getAllListInfo(listRepository: ListRepository): BookList?

    fun getListOfBooks(): ArrayList<Book>

    fun getDescription(): String

    fun getName(): String

    fun getBookCount(): Int

    fun getId(): String

    fun canModify(): Boolean

    fun getPrivacy(): ListPrivacy
}