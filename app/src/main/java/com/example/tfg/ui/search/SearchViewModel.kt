package com.example.tfg.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.Book
import java.time.LocalDate

sealed class SearchScreenEvent {
    data class UserQueryChange(val userQuery: String) : SearchScreenEvent()
    object GetResultsFromQuery : SearchScreenEvent()
}

data class SearchMainState(
    var userQuery: String = "",
    var queryResult: List<Book> = emptyList(),
    var expandedSearchBar: Boolean = false

)

class SearchViewModel : ViewModel() {
    var searchInfo by mutableStateOf(SearchMainState())
    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.UserQueryChange -> {
                searchInfo = searchInfo.copy(userQuery = event.userQuery)
            }
            is SearchScreenEvent.GetResultsFromQuery -> {
                getResultsFromQuery()
            }
        }
    }

    private fun getResultsFromQuery() {
        val items = listOf(
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba, pages = 789, publicationDate = LocalDate.ofYearDay(2017,12)),
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba, pages = 789, publicationDate = LocalDate.ofYearDay(2017,12)),
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba, pages = 789, publicationDate = LocalDate.ofYearDay(2017,12)),
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba, pages = 789, publicationDate = LocalDate.ofYearDay(2017,12)),
        )

//        TODO:Se ejecuta la query con los parámetros correspondientes

        searchInfo = searchInfo.copy(queryResult = items)
    }
}