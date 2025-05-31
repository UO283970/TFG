package com.example.tfg.search

import androidx.lifecycle.SavedStateHandle
import com.example.tfg.model.book.Book
import com.example.tfg.model.book.BookState
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.BookRepository
import com.example.tfg.repository.ListRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.search.SearchViewModel
import com.example.tfg.ui.search.components.OrderByEnum
import com.example.tfg.ui.search.components.SearchForEnum
import com.example.tfg.ui.search.components.SubjectsEnum
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class SearchViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: SearchViewModel
    private lateinit var bookRepository: BookRepository
    private lateinit var listRepository: ListRepository
    private lateinit var stringResourcesProvider: StringResourcesProvider
    private lateinit var listsState: ListsState
    private lateinit var bookState: BookState
    private lateinit var savedStateHandle: SavedStateHandle

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        bookRepository = mockk()
        listRepository = mockk()
        stringResourcesProvider = mockk()
        listsState = mockk()
        bookState = BookState()
        savedStateHandle = SavedStateHandle()

        viewModel = SearchViewModel(
            stringResourcesProvider,
            bookRepository,
            listRepository,
            listsState,
            bookState,
            savedStateHandle
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `userQueryChange updates the state`() {
        viewModel.userQueryChange("lord of the rings")
        assertEquals("lord of the rings", viewModel.searchInfo.userQuery)
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `toggleButtonSheet toggles the bottom sheet state`() {
        val initial = viewModel.searchInfo.isBottomSheetOpened
        viewModel.toggleButtonSheet()
        assertEquals(!initial, viewModel.searchInfo.isBottomSheetOpened)
    }

    @Test
    fun `orderBy results by default`(){
        viewModel.getButtonToOrderByMap()

        val map = viewModel.searchInfo.orderByButtonMap

        for (entry in OrderByEnum.entries) {
            if (entry == OrderByEnum.DEFAULT) {
                assertTrue(map[entry] == true)
            } else {
                assertTrue(map[entry] == false)
            }
        }
    }

    @Test
    fun `changeOrderByButton sets the selected order and unselects others`() {
        viewModel.getButtonToOrderByMap()
        viewModel.changeOrderByButton(OrderByEnum.AUTHOR)

        val selected = viewModel.searchInfo.orderByButtonMap.entries.filter { it.value }
        assertEquals(1, selected.size)
        assertEquals(OrderByEnum.AUTHOR, selected[0].key)
    }

    @Test
    fun `changeSelectedSearchFor sets the search for`() {
        viewModel.changeSelectedSearchFor(SearchForEnum.BOOKS)

        var selected = viewModel.searchInfo.searchFor
        assertEquals(selected, SearchForEnum.BOOKS)

        viewModel.userQueryChange("Searched")
        viewModel.changeSelectedSearchFor(SearchForEnum.BOOKS)


        selected = viewModel.searchInfo.searchFor
        assertEquals(selected, SearchForEnum.BOOKS)
    }

    @Test
    fun `change the selected subject and change to the same`() {
        viewModel.changeSelectedSubject(SubjectsEnum.FANTASY)
        assertEquals(SubjectsEnum.FANTASY, viewModel.searchInfo.subject)
        viewModel.changeSelectedSubject(SubjectsEnum.FANTASY)
        assertEquals(null, viewModel.searchInfo.subject)
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `order by any field descending and ascending`() {
        val book1 = Book(title = "ABook", author = "Author A")
        val book2 = Book(title = "BBook", author = "Author B")
        viewModel.defaultSearchResult = arrayListOf(book1, book2)


        viewModel.orderBy(OrderByEnum.TITLE, descending = false)


        var sorted = viewModel.searchInfo.queryResult
        assertEquals(2, sorted.size)
        assertEquals("ABook", sorted[0].title)
        assertEquals("BBook", sorted[1].title)

        viewModel.orderBy(OrderByEnum.TITLE, descending = true)


        sorted = viewModel.searchInfo.queryResult
        assertEquals(2, sorted.size)
        assertEquals("BBook", sorted[0].title)
        assertEquals("ABook", sorted[1].title)
    }

    @Test
    fun `getResultsFromQuery should return a book`() {
        val book = Book(
            title = "Title",
            author = "Author",
        )
        val book2 = Book(
            title = "OtherTitle",
            author = "Author",
        )

        coEvery { bookRepository.searchBooks(any(), any(), any()) } returns listOf(book,book2)

        viewModel.getResultsFromQuery()
        testDispatcher.scheduler.advanceUntilIdle()

        var result = viewModel.searchInfo.queryResult
        assertEquals(2, result.size)
        assertEquals("Title", result[0].title)
        assertEquals("OtherTitle", result[1].title)

        coEvery { bookRepository.searchBooks(any(), any(), any()) } returns null
        viewModel.changeSelectedSearchFor(SearchForEnum.BOOKS)
        viewModel.changeSelectedSubject(SubjectsEnum.FANTASY)
        viewModel.getResultsFromQuery()
        testDispatcher.scheduler.advanceUntilIdle()

        result = viewModel.searchInfo.queryResult
        assertEquals(0, result.size)
    }

    @Test
    fun `search for more books`() {
        val book = Book(
            title = "Title",
            author = "Author",
        )
        val book2 = Book(
            title = "OtherTitle",
            author = "Author",
        )

        coEvery { bookRepository.nextPageBooks(any(), any(), any(), any()) } returns listOf(book,book2)

        viewModel.addMoreBooksForQuery()
        testDispatcher.scheduler.advanceUntilIdle()

        var result = viewModel.searchInfo.queryResult
        assertEquals(2, result.size)
        assertEquals(book.title, result[0].title)
        assertEquals(book2.title, result[1].title)

        coEvery { bookRepository.nextPageBooks(any(), any(), any(), any()) } returns null

        viewModel.addMoreBooksForQuery()
        testDispatcher.scheduler.advanceUntilIdle()

        result = viewModel.searchInfo.queryResult
        assertEquals(2, result.size)

        coEvery { bookRepository.nextPageBooks(any(), any(), any(), any()) } returns listOf()

        viewModel.addMoreBooksForQuery()
        testDispatcher.scheduler.advanceUntilIdle()

        result = viewModel.searchInfo.queryResult
        assertEquals(2, result.size)
    }

    @Test
    fun `new book for details`() {
        val book = Book(
            title = "Title",
            author = "Author",
        )

        viewModel.setBookForDetails(book)

        val result = bookState.bookForDetails
        assertEquals(book, result)
        assertEquals(book.title, result.title)
    }

}