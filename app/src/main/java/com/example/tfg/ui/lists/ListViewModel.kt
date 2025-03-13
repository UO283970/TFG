package com.example.tfg.ui.lists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.BookList
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.common.navHost.ListNavigationItems
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDate

data class ListMainState(
    var userQuery: String = "",
    var tabIndex: Int = 0,
    val tabs: List<String> = emptyList(),
    var ownLists: List<BookList> = arrayListOf(),
    var defaultLists: List<BookList> = arrayListOf()
)

class ListViewModel(
    private val navController: NavController,
    private val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {
    var listState by mutableStateOf(
        ListMainState()
            .copy(
                tabs = stringResourcesProvider.getStringArray(R.array.list_of_lists_tabs),
                defaultLists = getDefaultLists(), ownLists = getOwnLists()
            )
    )

    fun userQueryChange(query: String) {
        listState = listState.copy(userQuery = query)
    }

    fun tabChange(tabIndex: Int) {
        listState = listState.copy(tabIndex = tabIndex)
    }

    fun listDetails(list:BookList) {
        listState = listState.copy(tabIndex = listState.tabIndex)
        val gson: Gson = GsonBuilder().create()
        val listJson = gson.toJson(list)
        navController.navigate(
            ListNavigationItems.ListDetails.route.replace(
                oldValue = "{list}",
                newValue = listJson
            )
        )
    }

    fun navigationToCreationListScreen() {
        navController.navigate(ListNavigationItems.ListCreation.route)
    }

    private fun getOwnLists(): List<BookList> {
        /*TODO: Conseguir las listas del usuario*/
        val forTest = Book(
            "Words Of Radiance",
            "Brandon Sanderson",
            R.drawable.prueba,
            pages = 789,
            publicationDate = LocalDate.ofYearDay(2017, 12)
        )

        return arrayListOf(BookList("Fantasia interesante", arrayListOf(forTest)))
    }

    private fun getDefaultLists(): List<BookList> {
        /*TODO: Conseguir las listas por defecto del usuario*/
        val listNames = stringResourcesProvider.getStringArray(R.array.list_of_default_lists)
        val listOfBooks: ArrayList<BookList> = arrayListOf()
        val forTest = Book(
            "Words Of Radiance",
            "Brandon Sanderson",
            R.drawable.prueba,
            pages = 789,
            publicationDate = LocalDate.ofYearDay(2017, 12)
        )

        for (name in listNames) {
            listOfBooks.add(BookList(name, arrayListOf(forTest)))
        }

        return listOfBooks
    }
}