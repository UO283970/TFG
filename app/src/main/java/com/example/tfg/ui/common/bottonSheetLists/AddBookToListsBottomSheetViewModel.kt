package com.example.tfg.ui.common.bottonSheetLists

import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.booklist.BookList
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class SheetListsSate(
    val checkboxDefaultList: MutableMap<String, Boolean> = linkedMapOf<String, Boolean>(),
    val checkboxUserList: MutableMap<BookList, Boolean> = linkedMapOf<BookList, Boolean>(),
    val userQuery: String = "",
    var updateView: Boolean = false
)

@HiltViewModel
class AddBookToListsBottomSheetViewModel @Inject constructor(
    val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {
    private val _sheetListSate: MutableStateFlow<SheetListsSate> = MutableStateFlow(SheetListsSate())
    var sheetListSate = _sheetListSate.asStateFlow()

    init {
        generateDefaultLists()
        generateUsrLists()
    }

    fun changeSelectedDefaultList(bookListName: String, boolean: Boolean) {
        for (list in _sheetListSate.value.checkboxDefaultList) {
            list.setValue(false)
        }

        _sheetListSate.value.checkboxDefaultList.put(bookListName, boolean)
        _sheetListSate.value = _sheetListSate.value.copy(updateView = !_sheetListSate.value.updateView)

    }

    fun changeUserListState(bookList: BookList, boolean: Boolean) {
        _sheetListSate.value.checkboxUserList.put(bookList, !boolean)
        _sheetListSate.value = _sheetListSate.value.copy(updateView = !_sheetListSate.value.updateView)

    }

    fun onClose() {
        //TODO: Guardas en la base de datos los cambios
    }

    private fun generateDefaultLists(){
        var booleanMap = LinkedHashMap<String, Boolean>()
        var defaultList = stringResourcesProvider.getStringArray(R.array.list_of_default_lists)
        for (list in defaultList) {
            booleanMap.put(list, false)
        }

        _sheetListSate.value = _sheetListSate.value.copy(checkboxDefaultList = booleanMap)
    }

    private fun generateUsrLists() {
        /*TODO: Buscar la listas del usuario y si el libro esta incluido en alguna poner el check a true*/
        var booleanMap = mutableMapOf<BookList, Boolean>()
        var defaultList = stringResourcesProvider.getStringArray(R.array.list_of_default_lists)
        for (list in defaultList) {
            booleanMap.put(BookList("",list), false)
        }

        _sheetListSate.value = _sheetListSate.value.copy(checkboxUserList = booleanMap)
    }

}