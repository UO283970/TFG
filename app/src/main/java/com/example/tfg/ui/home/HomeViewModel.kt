package com.example.tfg.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class HomeMainState(
    var listOfBooks : ArrayList<Book>,
    var listOfReadingBooks : ArrayList<Book>
)

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel(){

    var homeState by mutableStateOf(HomeMainState(getListOfRecommendedBooks(),getReadingBooks()))

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

    private fun getReadingBooks() : ArrayList<Book>{
        //TODO : Obtener libros que se están leyendo, es decir, libros en la lista leyendo
        return arrayListOf()
    }

}