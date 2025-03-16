package com.example.tfg.ui.lists.listDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tfg.model.booklist.BookList
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ListDetailsMainState(
    var bookList: BookList?,
    var userQuery: String = ""

)

@HiltViewModel
class ListDetailsViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    val gson: Gson = GsonBuilder().create()
    val bookListJson = savedStateHandle.get<String?>("bookList")
    val bookList: BookList? = gson.fromJson(bookListJson, BookList::class.java)

    var listDetailsInfo by mutableStateOf(ListDetailsMainState(bookList))
    fun userQueryChange(query: String) {
        listDetailsInfo = listDetailsInfo.copy(userQuery = query)
    }
}