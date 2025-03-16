package com.example.tfg.ui.lists.listCreation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.AppConstants
import com.example.tfg.model.booklist.ListPrivacy
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ListCreationMainState(
    var listName: String = "",
    var listNameError: String? = null,
    var listDescription: String = "",
    var listPrivacy: ListPrivacy = ListPrivacy.PUBLIC,
    var dropDawnExpanded: Boolean = false
)

@HiltViewModel
class ListCreationViewModel @Inject constructor(
    val stringResourcesProvider: StringResourcesProvider
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

    fun saveNewList(): Boolean {
        return checkListName()
        /*TODO: Guardar la lista creada*/
    }

    private fun checkListName(): Boolean {
        /*TODO: Mirar que no haya dos listas con el mismo nombre*/
        if (listCreationState.listName.isBlank()) {
            listCreationState =
                listCreationState.copy(listNameError = stringResourcesProvider.getString(R.string.list_creation_list_empty_name_error))
            return false
        }

        return true
    }
}