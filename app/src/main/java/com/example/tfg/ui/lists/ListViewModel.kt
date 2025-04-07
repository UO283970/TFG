package com.example.tfg.ui.lists

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.R
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.GlobalErrorHandler
import com.example.tfg.repository.ListRepository
import com.example.tfg.repository.exceptions.AuthenticationException
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.common.navHost.ListNavigationItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ListMainState(
    var userQuery: String = "",
    var tabIndex: Int = 0,
    val tabs: List<String>,
    var listsState: ListsState,
    var change: Boolean = false,
    var userId: String,
    var canAddLists: Boolean
)

@HiltViewModel
class ListViewModel @Inject constructor(
    var stringResourcesProvider: StringResourcesProvider,
    savedStateHandle: SavedStateHandle,
    private val listRepository: ListRepository,
    val listsState: ListsState
) : ViewModel() {

    val userId = if(savedStateHandle.get<String?>("userId") == null) "" else savedStateHandle.get<String?>("userId")

    private var _listState = MutableStateFlow(
        savedStateHandle.get<ListMainState>("listScreenInfo") ?: ListMainState(
            tabs = stringResourcesProvider.getStringArray(R.array.list_of_lists_tabs),
            listsState = listsState,
            userId = userId!!,
            canAddLists = userId == ""
        )
    )

    var listState: StateFlow<ListMainState> = _listState

    init {
        if(_listState.value.listsState.getOwnLists().isEmpty() && _listState.value.userId == ""){
            getDefaultLists()
            getOwnLists()
        }
        savedStateHandle["userId"] = ""
    }

    fun userQueryChange(query: String) {
        _listState.value = _listState.value.copy(userQuery = query)
    }

    fun tabChange(tabIndex: Int) {
        _listState.value = _listState.value.copy(tabIndex = tabIndex)
    }

    fun listDetails(bookList : BookList) {
        _listState.value.listsState.setDetailsList(bookList)
    }

    fun listCreation(): String {
        return ListNavigationItems.ListCreation.route
    }

    private fun getOwnLists(): List<BookListClass> {
        var resultList = arrayListOf<BookListClass>()

        viewModelScope.launch {
            try {
                var userList = listRepository.getBasicListInfo(userId = _listState.value.userId)
                if (userList != null) {
                    _listState.value.listsState.setOwnList(ArrayList(userList))
                }
                _listState.value = _listState.value.copy(change = !_listState.value.change)
            } catch (e: AuthenticationException) {
                GlobalErrorHandler.handle(e)
            }
        }

        return resultList
    }

    private fun getDefaultLists() {
        viewModelScope.launch {
            try {
                val defaultUserLists = listRepository.getUserDefaultLists(_listState.value.userId)
                if (defaultUserLists != null) {
                    _listState.value.listsState.setDefaultList(ArrayList(defaultUserLists))
                }
                _listState.value = _listState.value.copy(change = !_listState.value.change)
            } catch (e: AuthenticationException) {
                GlobalErrorHandler.handle(e)
            }
        }
    }
}