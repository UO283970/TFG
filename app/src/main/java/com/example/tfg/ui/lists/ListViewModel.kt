package com.example.tfg.ui.lists

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.BookList
import com.example.tfg.repository.ListRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.common.navHost.ListNavigationItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import javax.inject.Inject

@Parcelize
data class ListMainState(
    var userQuery: String = "",
    var tabIndex: Int = 0,
    val tabs: List<String>,
    var ownLists: List<BookList>,
    var defaultLists: List<BookList>
) : Parcelable

@HiltViewModel
class ListViewModel @Inject constructor(
    private val stringResourcesProvider: StringResourcesProvider,
    private var savedStateHandle: SavedStateHandle,
    private val listRepository: ListRepository
) : ViewModel() {

    private val _listState = MutableStateFlow(
        savedStateHandle.get<ListMainState>("listScreenInfo") ?: ListMainState(
            tabs = stringResourcesProvider.getStringArray(R.array.list_of_lists_tabs),
            defaultLists = getDefaultLists(),
            ownLists = getOwnLists()
        )
    )

    var listState: StateFlow<ListMainState> = _listState

    fun userQueryChange(query: String) {
        _listState.value = _listState.value.copy(userQuery = query)
    }

    fun tabChange(tabIndex: Int) {
        _listState.value = _listState.value.copy(tabIndex = tabIndex)
    }

    fun listDetails(): String {
        savedStateHandle["listScreenInfo"] = listState.value
        return ListNavigationItems.ListDetails.route
    }

    private fun getOwnLists(): List<BookList> {
        var resultList = arrayListOf<BookList>()

        viewModelScope.launch {
            var userList = listRepository.getBasicListInfo(userId = "")
            if (userList != null) {
                for(list in userList){
                    resultList.add(BookList(list.listName))
                }
            }
        }

        return resultList
    }

    private fun getDefaultLists(): List<BookList> {
        /*TODO: Conseguir las listas por defecto del usuario*/
        val listNames = stringResourcesProvider.getStringArray(R.array.list_of_default_lists)
        val listOfBooks: ArrayList<BookList> = arrayListOf()
        val forTest = Book(
            "Words Of Radiance",
            "Brandon Sanderson",
            R.drawable.prueba,
            pages = 789,
            publicationDate = LocalDate.ofYearDay(2017, 12)
        )

        for (name in listNames) {
            listOfBooks.add(BookList(name, arrayListOf(forTest)))
        }

        return listOfBooks
    }
}