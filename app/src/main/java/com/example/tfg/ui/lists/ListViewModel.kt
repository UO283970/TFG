package com.example.tfg.ui.lists

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.R
import com.example.tfg.model.booklist.BookList
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
    var userListState: UserListState,
    var change: Boolean = false
)

@HiltViewModel
class ListViewModel @Inject constructor(
    var stringResourcesProvider: StringResourcesProvider,
    savedStateHandle: SavedStateHandle,
    private val listRepository: ListRepository,
    val userListState: UserListState
) : ViewModel() {

    private var _listState = MutableStateFlow(
        savedStateHandle.get<ListMainState>("listScreenInfo") ?: ListMainState(
            tabs = stringResourcesProvider.getStringArray(R.array.list_of_lists_tabs),
            userListState = userListState
        )
    )

    var listState: StateFlow<ListMainState> = _listState

    init{
        getDefaultLists()
        getOwnLists()
    }

    fun userQueryChange(query: String) {
        _listState.value = _listState.value.copy(userQuery = query)
    }

    fun tabChange(tabIndex: Int) {
        _listState.value = _listState.value.copy(tabIndex = tabIndex)
    }

    fun listDetails(): String {
        return ListNavigationItems.ListDetails.route
    }

    fun listCreation(): String {
        return ListNavigationItems.ListCreation.route
    }

    private fun getOwnLists(): List<BookList> {
        var resultList = arrayListOf<BookList>()

        viewModelScope.launch {
            var userList = listRepository.getBasicListInfo(userId = "")
            if (userList != null) {
                _listState.value.userListState.setOwnList(ArrayList(userList))
                _listState.value = _listState.value.copy(change = !_listState.value.change)
            }
        }

        return resultList
    }

    private fun getDefaultLists() {
        viewModelScope.launch {
            try{
                val defaultUserLists = listRepository.getUserDefaultLists("")
                if(defaultUserLists != null){
                    _listState.value.userListState.setDefaultList(ArrayList(defaultUserLists))
                    _listState.value = _listState.value.copy(change = !_listState.value.change)
                }
            }catch (e: AuthenticationException){
                GlobalErrorHandler.handle(e)
            }
        }
    }
}