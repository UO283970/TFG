package com.example.tfg.model.booklist

import android.os.Parcelable
import com.example.tfg.model.Book
import com.example.tfg.repository.ListRepository

interface BookList : Parcelable {

    suspend fun getAllListInfo(listRepository: ListRepository): BookList?

    fun getListOfBooks(): ArrayList<Book>

    fun getDescription(): String

    fun getName(): String

    fun getBookCount(): Int

}