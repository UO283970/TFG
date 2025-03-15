package com.example.tfg.ui.lists.listDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.model.booklist.BookList
import com.example.tfg.ui.common.CommonEventHandler

data class ListDetailsMainState(
    var bookList: BookList,
    var userQuery: String = ""

)

class ListDetailsViewModel(
    bookList: BookList,
    val commonEventHandler: CommonEventHandler
) : ViewModel(){
    var listDetailsInfo by mutableStateOf(ListDetailsMainState(bookList))
    fun userQueryChange(query: String) {
        listDetailsInfo = listDetailsInfo.copy(userQuery = query)
    }
}