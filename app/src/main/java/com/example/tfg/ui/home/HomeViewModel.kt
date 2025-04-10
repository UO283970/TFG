package com.example.tfg.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeMainState(
    var listOfBooks: ArrayList<Book>,
    var listOfReadingBooks: ArrayList<Book>
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val listRepository: ListRepository,
    val listsState: ListsState
) : ViewModel() {

    private val _homeState = MutableStateFlow(
        savedStateHandle.get<HomeMainState>("homeInfo") ?: HomeMainState(
            getListOfRecommendedBooks(),
            getReadingBooks()
        )
    )

    var homeState: StateFlow<HomeMainState> = _homeState

    init {
        viewModelScope.launch {
            val userLists = listRepository.getBasicListInfo(userId = "")
            if(userLists != null){
                listsState.setOwnList(ArrayList(userLists))
            }
            val defaultList = listRepository.getUserDefaultLists(userId = "")
            if(defaultList != null){
                listsState.setDefaultList(ArrayList(defaultList))
            }
        }
    }

    private fun getListOfRecommendedBooks(): ArrayList<Book> {
        val items = arrayListOf<Book>(
            Book("Words Of Radiance", "Brandon Sanderson"),
            Book("Words Of Radiance", "Brandon Sanderson"),
            Book("Words Of Radiance", "Brandon Sanderson"),
            Book("Words Of Radiance", "Brandon Sanderson")
        )

        //viewModelScope.launch {  }
        //TODO : Obtener unos libros recomendados

        return items
    }

    private fun getReadingBooks(): ArrayList<Book> {
        //TODO : Obtener libros que se están leyendo, es decir, libros en la lista leyendo
        return arrayListOf()
    }

}