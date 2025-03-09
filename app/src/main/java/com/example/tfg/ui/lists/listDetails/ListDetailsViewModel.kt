package com.example.tfg.ui.lists.listDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tfg.model.BookList

sealed class ListDetailsScreenEvent {
    companion object GoBackEvent: ListDetailsScreenEvent()
    data class UserQueryChange(val query: String): ListDetailsScreenEvent()
}

data class ListDetailsMainState(
    var bookList: BookList = BookList(""),
    var userQuery: String = ""
)

class ListDetailsViewModel(
    private val navController: NavController,
    bookList: BookList
) : ViewModel(){
    var listDetailsInfo by mutableStateOf(ListDetailsMainState().copy(bookList = bookList))

    fun onEvent(event: ListDetailsScreenEvent){
        when(event){
            is ListDetailsScreenEvent.GoBackEvent -> {
                navController.popBackStack()
            }
            is ListDetailsScreenEvent.UserQueryChange -> {
                listDetailsInfo = listDetailsInfo.copy(userQuery = event.query)
            }
        }
    }
}