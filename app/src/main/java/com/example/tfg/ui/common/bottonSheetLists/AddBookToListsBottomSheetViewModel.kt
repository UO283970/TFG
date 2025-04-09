package com.example.tfg.ui.common.bottonSheetLists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.GlobalErrorHandler
import com.example.tfg.repository.ListRepository
import com.example.tfg.repository.exceptions.AuthenticationException
import com.example.tfg.ui.common.StringResourcesProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SheetListsSate(
    val checkboxDefaultList: MutableMap<DefaultList, Boolean> = linkedMapOf<DefaultList, Boolean>(),
    val selectedDefaultList: DefaultList? = null,
    val checkboxUserList: MutableMap<BookListClass, Boolean> = linkedMapOf<BookListClass, Boolean>(),
    val listOfSelectedUserLists: ArrayList<String> = arrayListOf<String>(),
    val listOfDeleteFromUserLists: ArrayList<String> = arrayListOf<String>(),
    val listOfCanDeleteIds: ArrayList<String> = arrayListOf<String>(),
    val userQuery: String = "",
    var updateView: Boolean = false
)

class AddBookToListsBottomSheetViewModel(
    val stringResourcesProvider: StringResourcesProvider,
    val listsRepository: ListRepository,
    val bookId: String,
    val listsState: ListsState
) : ViewModel() {
    private val _sheetListSate: MutableStateFlow<SheetListsSate> = MutableStateFlow(SheetListsSate())
    var sheetListSate = _sheetListSate.asStateFlow()

    init {
        generateDefaultLists()
        generateUsrLists()
    }

    fun changeSelectedDefaultList(bookList: DefaultList, boolean: Boolean) {
        for (list in _sheetListSate.value.checkboxDefaultList) {
            if(list.value){
                listsState.removeBookFromDefaultList(Book(bookId = bookId),list.key)
            }
            list.setValue(false)
        }

        _sheetListSate.value.checkboxDefaultList.put(bookList, boolean)
        if (boolean) {
            _sheetListSate.value = _sheetListSate.value.copy(selectedDefaultList = bookList)
            listsState.addBookToDefaultList(Book(bookId = bookId),bookList)
        } else {
            _sheetListSate.value = _sheetListSate.value.copy(selectedDefaultList = null)
        }

        _sheetListSate.value = _sheetListSate.value.copy(updateView = !_sheetListSate.value.updateView)

    }

    fun changeUserListState(bookListClass: BookListClass, boolean: Boolean) {
        _sheetListSate.value.checkboxUserList.put(bookListClass, !boolean)

        if(!boolean){
            _sheetListSate.value.listOfSelectedUserLists.add(bookListClass.listId)
            listsState.addBookToUserList(Book(bookId = bookId),bookListClass)
            if(_sheetListSate.value.listOfCanDeleteIds.contains(bookListClass.listId)){
                _sheetListSate.value.listOfDeleteFromUserLists.remove(bookListClass.listId)
            }
        }else{
            _sheetListSate.value.listOfSelectedUserLists.remove(bookListClass.listId)
            listsState.removeBookFromUserList(Book(bookId = bookId),bookListClass)
            if(_sheetListSate.value.listOfCanDeleteIds.contains(bookListClass.listId)){
                _sheetListSate.value.listOfDeleteFromUserLists.add(bookListClass.listId)
            }
        }

        _sheetListSate.value = _sheetListSate.value.copy(updateView = !_sheetListSate.value.updateView)

    }

    fun onClose() {
        viewModelScope.launch {
            if (_sheetListSate.value.selectedDefaultList != null) {
                try {
                    listsRepository.addBookToDefaultList(_sheetListSate.value.selectedDefaultList!!.listId, bookId)
                } catch (e: AuthenticationException) {
                    GlobalErrorHandler.handle(e)
                }
            }
            if(_sheetListSate.value.listOfSelectedUserLists.isNotEmpty()){
                try {
                    listsRepository.removeBookFromList(_sheetListSate.value.listOfDeleteFromUserLists, bookId)
                } catch (e: AuthenticationException) {
                    GlobalErrorHandler.handle(e)
                }
            }
            if(_sheetListSate.value.listOfDeleteFromUserLists.isNotEmpty()){
                try {
                    listsRepository.removeBookFromList(_sheetListSate.value.listOfDeleteFromUserLists, bookId)
                } catch (e: AuthenticationException) {
                    GlobalErrorHandler.handle(e)
                }
            }
        }
    }

    private fun generateDefaultLists() {
        viewModelScope.launch {
            val booksLocation = listsRepository.getDefaultListsWithBook(bookId)
            if (booksLocation != null) {
                val mapWithBookLocations: LinkedHashMap<DefaultList, Boolean> = listsState.getDefaultLists().associateWith { it.listId in booksLocation }.toMap(
                    LinkedHashMap()
                )

                _sheetListSate.value = _sheetListSate.value.copy(checkboxDefaultList = mapWithBookLocations)
            }
        }
    }

    private fun generateUsrLists() {
        viewModelScope.launch {
            val booksLocation = listsRepository.getListsWithBook(bookId)
            if (booksLocation != null) {
                val mapWithBookLocations: LinkedHashMap<BookListClass, Boolean> = listsState.getOwnLists().associateWith { it.listId in booksLocation }.toMap(
                    LinkedHashMap()
                )
                _sheetListSate.value = _sheetListSate.value.copy(listOfCanDeleteIds = ArrayList(booksLocation))
                _sheetListSate.value = _sheetListSate.value.copy(checkboxUserList = mapWithBookLocations)
            }
        }
    }
}