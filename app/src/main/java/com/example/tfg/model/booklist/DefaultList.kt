package com.example.tfg.model.booklist

import android.os.Parcelable
import com.example.tfg.R
import com.example.tfg.model.Book
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class DefaultList(var listId: String,
                       var listName: Int,
                       var listImage: Int = R.drawable.prueba,
                       var books: ArrayList<Book> = arrayListOf(),
                       var listDescription: String = "",
                       var numberOfBooks: Int = 0) : Parcelable
