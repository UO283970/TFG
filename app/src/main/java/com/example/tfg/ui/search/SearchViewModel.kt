package com.example.tfg.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.book.Book
import com.example.tfg.model.book.BookState
import com.example.tfg.model.booklist.DefaultListNames
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.BookRepository
import com.example.tfg.repository.ListRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.search.components.OrderByEnum
import com.example.tfg.ui.search.components.SearchForEnum
import com.example.tfg.ui.search.components.SubjectsEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class SearchMainState(
    var userQuery: String,
    var queryResult: ArrayList<Book> = arrayListOf<Book>(),
    var expandedSearchBar: Boolean = false,
    var isBottomSheetOpened: Boolean = false,
    var orderByButtonMap: MutableMap<OrderByEnum, Boolean> = linkedMapOf<OrderByEnum, Boolean>(),
    var searchFor: SearchForEnum,
    var subject: SubjectsEnum? = null,
    var forceRepaint: Boolean = false,
    var chargingInfo: Boolean = false,
    var actualPages: Int = 1,
    var loadMoreInfo: Boolean = false,
    val canGetMoreInfo: Boolean = false,
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    val stringResourcesProvider: StringResourcesProvider,
    val bookRepository: BookRepository,
    val listsRepository: ListRepository,
    val listsState: ListsState,
    val bookState: BookState,
    val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val userQuery = savedStateHandle.get<String?>("userQuery") ?: ""
    val searchFor = savedStateHandle.get<String?>("searchFor") ?: SearchForEnum.BOOKS.toString()

    var searchInfo by mutableStateOf(SearchMainState(userQuery, searchFor = SearchForEnum.valueOf(searchFor)))

    var defaultSearchResult = arrayListOf<Book>()

    init {
        getButtonToOrderByMap()
        if(searchInfo.userQuery != ""){
            getResultsFromQuery()
        }
    }

    fun userQueryChange(userQuery: String) {
        searchInfo = searchInfo.copy(userQuery = userQuery)
    }

    fun toggleButtonSheet() {
        searchInfo = searchInfo.copy(isBottomSheetOpened = !searchInfo.isBottomSheetOpened)
    }

    fun getButtonToOrderByMap() {
        for (order in OrderByEnum.entries.toTypedArray()) {
            searchInfo.orderByButtonMap.put(order, false)
        }

        searchInfo.orderByButtonMap.put(OrderByEnum.DEFAULT, true)
        searchInfo = searchInfo.copy(forceRepaint = !searchInfo.forceRepaint)
    }


    fun changeOrderByButton(orderByEnum: OrderByEnum) {
        for (orders in searchInfo.orderByButtonMap) {
            searchInfo.orderByButtonMap.put(orders.key, false)
        }
        searchInfo.orderByButtonMap.put(orderByEnum, true)
        searchInfo = searchInfo.copy(forceRepaint = !searchInfo.forceRepaint)
    }

    fun changeSelectedSearchFor(searchFor: SearchForEnum) {
        searchInfo = searchInfo.copy(searchFor = searchFor)
        if(searchInfo.userQuery != ""){
            getResultsFromQuery()
        }
    }

    fun changeSelectedSubject(subject: SubjectsEnum) {
        searchInfo = if(subject == searchInfo.subject){
            searchInfo.copy(subject = null)
        }else{
            searchInfo.copy(subject = subject)
        }
        getResultsFromQuery()
    }

    fun orderBy(orderByEnum: OrderByEnum, descending: Boolean) {
        searchInfo = searchInfo.copy(queryResult = arrayListOf<Book>())
        val listOrdered = orderByEnum.order(defaultSearchResult, descending)
        searchInfo = searchInfo.copy(queryResult = ArrayList(listOrdered))
    }

    fun getResultsFromQuery() {
        var resultFromQuery = arrayListOf<Book>()

        viewModelScope.launch {
            searchInfo = searchInfo.copy(chargingInfo = true)
            var subject = if(searchInfo.subject == null) "" else  "+subject:" + searchInfo.subject.toString()
            var searchFor = if(searchInfo.userQuery == "") "" else searchInfo.searchFor.searchForToQuery()
            var booksFromQuery = bookRepository.searchBooks(searchInfo.userQuery,searchFor,subject)
            if (booksFromQuery != null) {
                for (book in booksFromQuery) {
                    resultFromQuery.add(
                        Book(
                            book.title,
                            book.author,
                            book.coverImageURL,
                            pages = if(book.pages.isNotBlank()) Integer.valueOf(book.pages) else 0,
                            publicationDate =if(book.publishYear.isNotBlank()) LocalDate.ofYearDay(Integer.valueOf(book.publishYear), 12) else LocalDate.MIN,
                            bookId = book.bookId,
                            readingState = if(DefaultListNames.valueOf(book.readingState.toString()) != DefaultListNames.NOT_IN_LIST)
                                stringResourcesProvider.getString(DefaultListNames.valueOf(book.readingState.toString()).getDefaultListName())
                            else "",
                            subjects = book.subjects,
                            details = book.description ?: ""
                        )
                    )
                }
            }
            searchInfo = searchInfo.copy(queryResult = resultFromQuery)
            defaultSearchResult = resultFromQuery
            searchInfo = searchInfo.copy(canGetMoreInfo = true)
            searchInfo = searchInfo.copy(chargingInfo = false)
        }
    }

    fun addMoreBooksForQuery(){
        viewModelScope.launch {
            var newBooksQuery = bookRepository.nextPageBooks(searchInfo.userQuery, searchInfo.actualPages,searchInfo.searchFor.searchForToQuery(),"")
            if(newBooksQuery != null && newBooksQuery.isNotEmpty()){
                searchInfo.queryResult.addAll(newBooksQuery)
                searchInfo.actualPages++
                searchInfo = searchInfo.copy(loadMoreInfo = !searchInfo.loadMoreInfo)
            }
        }
    }

    fun setBookForDetails(book: Book){
        bookState.bookForDetails = book
    }
}