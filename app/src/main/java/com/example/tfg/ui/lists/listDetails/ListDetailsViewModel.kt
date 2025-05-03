package com.example.tfg.ui.lists.listDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.book.Book
import com.example.tfg.model.book.BookState
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.GlobalErrorHandler
import com.example.tfg.repository.ListRepository
import com.example.tfg.repository.exceptions.AuthenticationException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListDetailsMainState(
    var bookList: BookList? = null,
    var userQuery: String = "",
    var detailsLoaded: Boolean = false,
    var baseListOfBooks: List<Book> = arrayListOf<Book>(),
    var menuOpen: Boolean = false,
    var isDeleted: Boolean = false,
    var deleteDialog: Boolean = false,

)

@HiltViewModel
class ListDetailsViewModel @Inject constructor(val listsState: ListsState, val listRepository: ListRepository, val bookState: BookState) : ViewModel() {

    var listDetailsInfo by mutableStateOf(ListDetailsMainState())

    fun userQueryChange(query: String) {
        listDetailsInfo = listDetailsInfo.copy(userQuery = query)
    }

    init {
        viewModelScope.launch {
            listDetailsInfo = listDetailsInfo.copy(bookList = listsState.getDetailList().getAllListInfo(listRepository))
            listDetailsInfo = listDetailsInfo.copy(detailsLoaded = true)
            listDetailsInfo = listDetailsInfo.copy(baseListOfBooks = listDetailsInfo.bookList?.getListOfBooks() ?: arrayListOf())
        }
    }

    fun searchInList(){
        listDetailsInfo = listDetailsInfo.copy(detailsLoaded = false)
        if(!listDetailsInfo.userQuery.isBlank()){
            listDetailsInfo.baseListOfBooks = listDetailsInfo.bookList?.getListOfBooks()?.filter { it ->
                var tittleForSearch = it.tittle.trim().lowercase()
                tittleForSearch == listDetailsInfo.userQuery.trim().lowercase() || tittleForSearch.contains(listDetailsInfo.userQuery.trim().lowercase())
            }!!
        }else{
            listDetailsInfo.baseListOfBooks = listDetailsInfo.bookList?.getListOfBooks()!!
        }

        listDetailsInfo = listDetailsInfo.copy(detailsLoaded = true)
    }

    fun changeMenu(state: Boolean){
        listDetailsInfo = listDetailsInfo.copy(menuOpen = state)
    }


    fun toggleDeleteDialog(){
        listDetailsInfo = listDetailsInfo.copy(deleteDialog = listDetailsInfo.deleteDialog)
    }


    fun setBookForDetails(book: Book){
        bookState.bookForDetails = book
    }

    fun deleteList(){
        viewModelScope.launch {
            try{
                val deleted = listRepository.deleteList(listDetailsInfo.bookList?.getId() ?: "")
                if(deleted != null && deleted){
                    listDetailsInfo = listDetailsInfo.copy(isDeleted = true)
                    listsState.deleteList(listDetailsInfo.bookList as BookListClass)
                }
            }catch(e: AuthenticationException){
                GlobalErrorHandler.handle(e)
            }
        }
    }

}