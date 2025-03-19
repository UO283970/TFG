package com.example.tfg.ui.bookDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.BookList
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

data class BookDetails(
    var book: Book,
    var totalPages: String = "0"
)

data class MenuListSate(
    var listAddMenuOpen: Boolean = false,
    val checkboxDefaultList: LinkedHashMap<BookList, Boolean>,
    val checkboxUserList: MutableMap<BookList, Boolean>,
    val userQuery: String = "",
    val recompone: Boolean = false
)

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {
    var bookInfo by mutableStateOf(BookDetails(getBookInfo()))
    private val _menuListSate = MutableStateFlow(MenuListSate(checkboxDefaultList = getDefaultCheckList(), checkboxUserList = getUserCheckList()))
    val menuListSate = _menuListSate.asStateFlow()

    fun changePagesRead(pages: String) {
        val pattern = Regex("^\\d+\$")
        if (pages == "") {
            bookInfo = bookInfo.copy(totalPages = "")
            return
        }
        if (pages.matches(pattern) && Integer.valueOf(pages) <= bookInfo.book.pages) {
            bookInfo = bookInfo.copy(totalPages = pages)
        }
    }

    fun changeListAddMenuOpen(change: Boolean) {
        _menuListSate.value = _menuListSate.value.copy(listAddMenuOpen = change)
    }

    fun changeUserQuery(query: String) {
        _menuListSate.value = _menuListSate.value.copy(userQuery = query)
    }

    fun changeSelectedDefaultList(bookList: BookList, boolean: Boolean) {
        for (list in _menuListSate.value.checkboxDefaultList){
            list.setValue(false)
        }

        _menuListSate.value.checkboxDefaultList.put(bookList,boolean)
        _menuListSate.value = _menuListSate.value.copy(recompone = !_menuListSate.value.recompone)

//        _menuListSate.value = _menuListSate.value.copy(checkboxDefaultList = LinkedHashMap(_menuListSate.value.checkboxDefaultList).apply {
//            _menuListSate.value.checkboxDefaultList[bookList]?.let {
//                put(
//                    bookList,
//                    boolean
//                )
//            }
//        })
    }

    fun changeSelectedUserList(bookList: BookList, boolean: Boolean) {
        _menuListSate.value = _menuListSate.value.copy(checkboxUserList = LinkedHashMap(_menuListSate.value.checkboxUserList).apply {
            _menuListSate.value.checkboxUserList[bookList]?.let {
                put(
                    bookList,
                    boolean
                )
            }
        })
    }

    fun filterListByUserQuery(){
        /*TODO: Filtrar la lista por la búsqueda del usuario*/
    }

    private fun getBookInfo(): Book {
        //TODO: Obtener la info del libro
        return Book(
            "Words Of Radiance",
            "Brandon Sanderson",
            R.drawable.prueba,
            pages = 789,
            publicationDate = LocalDate.ofYearDay(2017, 12)
        )
    }


    private fun getDefaultCheckList(): LinkedHashMap<BookList, Boolean> {
        var booleanMap = LinkedHashMap<BookList, Boolean>()
        var defaultList = stringResourcesProvider.getStringArray(R.array.list_of_default_lists)
        for (list in defaultList) {
            booleanMap.put(BookList(list), false)
        }

        return booleanMap
    }

    private fun getUserCheckList(): MutableMap<BookList, Boolean> {
        /*TODO: Buscar la listas del usuario y si el libro esta incluido en alguna poner el check a true*/
        var booleanMap = mutableMapOf<BookList, Boolean>()
        var defaultList = stringResourcesProvider.getStringArray(R.array.list_of_default_lists)
        for (list in defaultList) {
            booleanMap.put(BookList(list), false)
        }

        return booleanMap
    }
}