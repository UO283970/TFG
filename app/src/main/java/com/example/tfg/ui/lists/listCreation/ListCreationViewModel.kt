package com.example.tfg.ui.lists.listCreation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.R
import com.example.tfg.model.AppConstants
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.booklist.ListPrivacy
import com.example.tfg.repository.ListRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.lists.UserListState
import com.graphQL.type.BookListPrivacy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListCreationMainState(
    var listName: String = "",
    var listNameError: String? = null,
    var listDescription: String = "",
    var listPrivacy: ListPrivacy = ListPrivacy.PUBLIC,
    var dropDawnExpanded: Boolean = false,
    var listCreated: Boolean = false
)

@HiltViewModel
class ListCreationViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val stringResourcesProvider: StringResourcesProvider,
    val listRepository: ListRepository,
    val userListState: UserListState
) : ViewModel() {

    var listCreationState by mutableStateOf(ListCreationMainState())

    fun changeListName(listName: String) {
        listCreationState = listCreationState.copy(listName = listName)
    }

    fun changeListDesc(listDescription: String) {
        if (listCreationState.listDescription.length < AppConstants.LIST_DESC_MAX_CHARACTERS)
            listCreationState = listCreationState.copy(listDescription = listDescription)
    }

    fun changeListPrivacy(listPrivacy: ListPrivacy) {
        listCreationState = listCreationState.copy(listPrivacy = listPrivacy)
    }

    fun changeDropDownState(expandedState: Boolean) {
        listCreationState = listCreationState.copy(dropDawnExpanded = expandedState)
    }

    fun saveNewList() {
        if (checkListName()) {
            viewModelScope.launch {
                val listCreated = listRepository.createList(
                    listCreationState.listName,
                    listCreationState.listDescription,
                    BookListPrivacy.valueOf(listCreationState.listPrivacy.toString())
                )
                if (listCreated != null) {
                    listCreationState = listCreationState.copy(listCreated = true)

                    userListState.addToOwnList(BookList(
                        listCreated,
                        listName = listCreationState.listName,
                        listDescription = listCreationState.listDescription,
                        listPrivacy = listCreationState.listPrivacy)
                    )

                } else {
                    listCreationState =
                        listCreationState.copy(listNameError = stringResourcesProvider.getString(R.string.list_creation_list_name_error))
                }
            }
        }
    }

    private fun checkListName(): Boolean {
        if (userListState.getOwnLists().stream().anyMatch { bookList -> bookList.listName == listCreationState.listName }) {
            listCreationState =
                listCreationState.copy(listNameError = stringResourcesProvider.getString(R.string.list_creation_list_repeated_name_error))
            return false
        }

        if (listCreationState.listName.isBlank()) {
            listCreationState =
                listCreationState.copy(listNameError = stringResourcesProvider.getString(R.string.list_creation_list_empty_name_error))
            return false
        }

        return true
    }
}