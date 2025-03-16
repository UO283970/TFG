package com.example.tfg.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class HomeMainState(
    var listOfBooks: ArrayList<Book>,
    var listOfReadingBooks: ArrayList<Book>
)

@HiltViewModel
class HomeViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _homeState = MutableStateFlow(
        savedStateHandle.get<HomeMainState>("homeInfo") ?: HomeMainState(
            getListOfRecommendedBooks(),
            getReadingBooks()
        )
    )

    var homeState: StateFlow<HomeMainState> = _homeState

    private fun getListOfRecommendedBooks(): ArrayList<Book> {
        val items = arrayListOf<Book>(
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba),
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba),
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba),
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba)
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