package com.example.tfg.model.booklist

import android.os.Parcelable
import com.example.tfg.model.Book
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
class BookList(
    var listId: String,
    var listName: String,
    var listImage: Int = 0,
    var books: ArrayList<Book> = arrayListOf(),
    var listDescription: String = "",
    var numberOfBooks: Int = 0,
    var listPrivacy: ListPrivacy = ListPrivacy.PUBLIC
) :
    Parcelable