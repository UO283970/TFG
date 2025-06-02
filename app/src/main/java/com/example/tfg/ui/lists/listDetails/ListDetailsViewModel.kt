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
import com.example.tfg.model.booklist.DefaultList
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
    var reload: Boolean = false,

    )

@HiltViewModel
class ListDetailsViewModel @Inject constructor(val listsState: ListsState, val listRepository: ListRepository, val bookState: BookState) : ViewModel() {

    var listDetailsInfo by mutableStateOf(ListDetailsMainState())

    fun userQueryChange(query: String) {
        listDetailsInfo = listDetailsInfo.copy(userQuery = query)
    }

    fun searchInList() {
        listDetailsInfo = listDetailsInfo.copy(detailsLoaded = false)
        if (!listDetailsInfo.userQuery.isBlank()) {
            listDetailsInfo.baseListOfBooks = listDetailsInfo.bookList?.getListOfBooks()?.filter { it ->
                var tittleForSearch = it.title.trim().lowercase()
                tittleForSearch == listDetailsInfo.userQuery.trim().lowercase() || tittleForSearch.contains(listDetailsInfo.userQuery.trim().lowercase())
            }!!
        } else {
            listDetailsInfo.baseListOfBooks = listDetailsInfo.bookList?.getListOfBooks()!!
        }

        listDetailsInfo = listDetailsInfo.copy(detailsLoaded = true)
    }

    fun loadInfo(){
        viewModelScope.launch {
            var bookList = listsState.getDetailList().getAllListInfo(listRepository)
            listDetailsInfo = listDetailsInfo.copy(
                bookList = bookList,
                detailsLoaded = true,
                baseListOfBooks = bookList?.getListOfBooks() ?: arrayListOf()
            )
            var defaultList = listsState.getDefaultLists().indexOfFirst { it.getId() == bookList?.getId() }
            var ownList = listsState.getOwnLists().indexOfFirst { it.getId() == bookList?.getId() }

            if(defaultList != -1 && bookList is DefaultList){
                listsState.getDefaultLists()[defaultList] = bookList
            }else{
                listsState.getOwnLists()[ownList] = bookList as BookListClass
            }
        }
    }

    fun changeMenu(state: Boolean) {
        listDetailsInfo = listDetailsInfo.copy(menuOpen = state)
    }


    fun toggleDeleteDialog() {
        listDetailsInfo = listDetailsInfo.copy(deleteDialog = !listDetailsInfo.deleteDialog)
    }


    fun setBookForDetails(book: Book) {
        bookState.bookForDetails = book
        listDetailsInfo = listDetailsInfo.copy(reload = true)
    }

    fun deleteList() {
        viewModelScope.launch {
            try {
                val deleted = listRepository.deleteList(listDetailsInfo.bookList?.getId() ?: "")
                if (deleted != null && deleted) {
                    listDetailsInfo = listDetailsInfo.copy(isDeleted = true)
                    listsState.deleteList(listDetailsInfo.bookList as BookListClass)
                }
            } catch (e: AuthenticationException) {
                GlobalErrorHandler.handle(e)
            }
        }
    }

}