package com.example.tfg.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.ui.common.navHost.Routes
import com.example.tfg.ui.switchTabs

sealed class HomeScreenEvent {
    object NavigateToSearch : HomeScreenEvent()
}

data class HomeMainState(
    var listOfBooks : List<Book> = emptyList(),
    var listOfReadingBooks : List<Book> = emptyList()
)

class HomeViewModel(val navController: NavHostController): ViewModel(){

    var homeState by mutableStateOf(HomeMainState())
    init {
        getListOfRecommendedBooks()
        getReadingBooks()
    }

    fun onEvent(event: HomeScreenEvent){
        when(event){
            is HomeScreenEvent.NavigateToSearch -> {
                navController.switchTabs(Routes.SearchScreen.route)
            }
        }
    }

    private fun getListOfRecommendedBooks() {
        val items = listOf(
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba),
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba),
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba),
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba)
        )

        //viewModelScope.launch {  }
        //TODO : Obtener unos libros recomendados

        homeState = homeState.copy(listOfBooks = items)
    }

    private fun getReadingBooks() {
        //TODO : Obtener libros que se están leyendo, es decir, libros en la lista leyendo
    }

}