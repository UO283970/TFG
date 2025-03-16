package com.example.tfg.model.booklist

import android.os.Parcelable
import com.example.tfg.model.Book
import kotlinx.parcelize.Parcelize

@Parcelize
class BookList(var listName: String, var books: ArrayList<Book> = arrayListOf(), var listDescription : String = "") :
    Parcelable