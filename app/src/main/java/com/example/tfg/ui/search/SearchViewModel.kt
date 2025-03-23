package com.example.tfg.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

data class SearchMainState(
    var userQuery: String = "",
    var queryResult: List<Book> = emptyList(),
    var expandedSearchBar: Boolean = false,
    var isBottomSheetOpened: Boolean = false,
    var orderByButtonMap: MutableMap<OrderByEnum, Boolean> = linkedMapOf<OrderByEnum, Boolean>(),
    var searchFor: SearchForEnum = SearchForEnum.BOOKS,
    var forceRepaint: Boolean = false

    )

@HiltViewModel
class SearchViewModel @Inject constructor(
    val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {
    var searchInfo by mutableStateOf(SearchMainState())

    init {
        getButtonToOrderByMap()
    }

    fun userQueryChange(userQuery: String) {
        searchInfo = searchInfo.copy(userQuery = userQuery)
    }

    fun toggleButtonSheet(){
        searchInfo = searchInfo.copy(isBottomSheetOpened = !searchInfo.isBottomSheetOpened)
    }

    fun getButtonToOrderByMap(){
        for (order in OrderByEnum.entries.toTypedArray()){
            searchInfo.orderByButtonMap.put(order,false)
        }

        searchInfo.orderByButtonMap.put(OrderByEnum.TITTLE,true)
        searchInfo = searchInfo.copy(forceRepaint = !searchInfo.forceRepaint)
    }

    fun changeOrderByButton(orderByEnum: OrderByEnum){
        for(orders in searchInfo.orderByButtonMap){
            searchInfo.orderByButtonMap.put(orders.key, false)
        }
        searchInfo.orderByButtonMap.put(orderByEnum, true)
        searchInfo = searchInfo.copy(forceRepaint = !searchInfo.forceRepaint)
    }

    fun changeSelectedSearchFor(searchFor: SearchForEnum){
        searchInfo = searchInfo.copy(searchFor = searchFor)
    }

    fun orderBy(orderByEnum: OrderByEnum, descending: Boolean){
        searchInfo = searchInfo.copy(queryResult = orderByEnum.order(searchInfo.queryResult, descending))
    }

    fun getResultsFromQuery() {
        val items = listOf(
            Book("Bords Of Radiance", "Brandon Sanderson", R.drawable.prueba, pages = 789, publicationDate = LocalDate.ofYearDay(2017,12), readingState = "Leyendo"),
            Book("Aords Of Radiance", "Brandon Sanderson", R.drawable.prueba, pages = 789, publicationDate = LocalDate.ofYearDay(2017,12), readingState = "Leyendo"),
            Book("Cords Of Radiance", "Brandon Sanderson", R.drawable.prueba, pages = 789, publicationDate = LocalDate.ofYearDay(2017,12), readingState = "Leyendo"),
            Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba, pages = 789, publicationDate = LocalDate.ofYearDay(2017,12), readingState = "Leyendo"),
        )

//        TODO:Se ejecuta la query con los parámetros correspondientes

        searchInfo = searchInfo.copy(queryResult = items)
    }
}