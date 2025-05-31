package com.example.tfg.ui.common.bottomSheetLists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.book.Book
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
    val selectedDefaultListId: String = "",
    val startSelectedDefaultListId: String = "",
    val checkboxUserList: MutableMap<BookListClass, Boolean> = linkedMapOf<BookListClass, Boolean>(),
    val listOfSelectedUserLists: ArrayList<String> = arrayListOf<String>(),
    val listOfDeleteFromUserLists: ArrayList<String> = arrayListOf<String>(),
    val listOfCanDeleteIds: ArrayList<String> = arrayListOf<String>(),
    val userQuery: String = "",
    var updateView: Boolean = false,
    var showToast: Boolean = false
)

class AddBookToListsBottomSheetViewModel(
    val stringResourcesProvider: StringResourcesProvider,
    val listsRepository: ListRepository,
    val book: Book,
    val listsState: ListsState
) : ViewModel() {
    private val _sheetListSate: MutableStateFlow<SheetListsSate> = MutableStateFlow(SheetListsSate())
    var sheetListSate = _sheetListSate.asStateFlow()

    init {
        generateDefaultLists()
        generateUsrLists()
    }

    fun toggleToast(){
        _sheetListSate.value = _sheetListSate.value.copy(showToast = !_sheetListSate.value.showToast)
    }

    fun changeSelectedDefaultList(bookList: DefaultList, boolean: Boolean) {
        if (boolean && _sheetListSate.value.selectedDefaultList != null) {
            _sheetListSate.value.checkboxDefaultList.put(_sheetListSate.value.selectedDefaultList!!, false)
            viewModelScope.launch {
                listsState.removeBookFromDefaultList(book, _sheetListSate.value.selectedDefaultList!!, listsRepository)
            }
        }

        _sheetListSate.value.checkboxDefaultList[bookList] = boolean
        if (boolean) {
            _sheetListSate.value = _sheetListSate.value.copy(selectedDefaultList = bookList)
            listsState.addBookToDefaultList(book, bookList)
        } else {
            _sheetListSate.value = _sheetListSate.value.copy(selectedDefaultList = null)
            viewModelScope.launch {
                listsState.removeBookFromDefaultList(book, bookList, listsRepository)
            }
        }

        _sheetListSate.value = _sheetListSate.value.copy(updateView = !_sheetListSate.value.updateView)

    }

    fun changeUserListState(bookListClass: BookListClass, boolean: Boolean) {
        _sheetListSate.value.checkboxUserList.put(bookListClass, !boolean)

        if (!boolean) {
            _sheetListSate.value.listOfSelectedUserLists.add(bookListClass.listId)
            listsState.addBookToUserList(book, bookListClass)
            if (_sheetListSate.value.listOfCanDeleteIds.contains(bookListClass.listId)) {
                _sheetListSate.value.listOfDeleteFromUserLists.remove(bookListClass.listId)
            }
        } else {
            _sheetListSate.value.listOfSelectedUserLists.remove(bookListClass.listId)
            viewModelScope.launch {
                listsState.removeBookFromUserList(book, bookListClass, listsRepository)
            }
            if (_sheetListSate.value.listOfCanDeleteIds.contains(bookListClass.listId)) {
                _sheetListSate.value.listOfDeleteFromUserLists.add(bookListClass.listId)
            }
        }

        _sheetListSate.value = _sheetListSate.value.copy(updateView = !_sheetListSate.value.updateView)

    }

    fun onClose() {
        viewModelScope.launch {
            if (_sheetListSate.value.selectedDefaultList != null) {
                try {
                    if (_sheetListSate.value.startSelectedDefaultListId != ""
                        && _sheetListSate.value.selectedDefaultList!!.listId != _sheetListSate.value.startSelectedDefaultListId
                    ) {
                        try {
                            listsRepository.removeBookFromDefaultList(_sheetListSate.value.startSelectedDefaultListId, book.bookId)
                        } catch (e: AuthenticationException) {
                            GlobalErrorHandler.handle(e)
                        }
                    }
                    if (_sheetListSate.value.selectedDefaultList!!.listId != _sheetListSate.value.selectedDefaultListId){
                        listsRepository.addBookToDefaultList(_sheetListSate.value.selectedDefaultList!!.listId, book.bookId)
                    }
                } catch (e: AuthenticationException) {
                    GlobalErrorHandler.handle(e)
                }
            } else {
                try {
                    listsRepository.removeBookFromDefaultList(_sheetListSate.value.selectedDefaultListId, book.bookId)
                } catch (e: AuthenticationException) {
                    GlobalErrorHandler.handle(e)
                }
            }
            if (_sheetListSate.value.listOfSelectedUserLists.isNotEmpty()) {
                try {
                    listsRepository.addBookToList(_sheetListSate.value.listOfSelectedUserLists, book.bookId)
                } catch (e: AuthenticationException) {
                    GlobalErrorHandler.handle(e)
                }
            }
            if (_sheetListSate.value.listOfDeleteFromUserLists.isNotEmpty()) {
                try {
                    listsRepository.removeBookFromList(_sheetListSate.value.listOfDeleteFromUserLists, book.bookId)
                } catch (e: AuthenticationException) {
                    GlobalErrorHandler.handle(e)
                }
            }
        }
    }

    private fun generateDefaultLists() {
        viewModelScope.launch {
            val booksLocation = listsRepository.getDefaultListsWithBook(book.bookId)
            if (booksLocation != null) {
                val mapWithBookLocations: LinkedHashMap<DefaultList, Boolean> = listsState.getDefaultLists().associateWith { it.listId in booksLocation }.toMap(
                    LinkedHashMap()
                )

                _sheetListSate.value = _sheetListSate.value.copy(checkboxDefaultList = mapWithBookLocations)
                _sheetListSate.value = _sheetListSate.value.copy(selectedDefaultList = mapWithBookLocations.keys.find { it.listId == booksLocation })
                _sheetListSate.value = _sheetListSate.value.copy(selectedDefaultListId = booksLocation)
                _sheetListSate.value = _sheetListSate.value.copy(startSelectedDefaultListId = booksLocation)
            }
        }
    }

    private fun generateUsrLists() {
        viewModelScope.launch {
            val booksLocation = listsRepository.getListsWithBook(book.bookId)
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