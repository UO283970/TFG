package com.example.tfg.lists.listDetails

import com.example.tfg.model.book.Book
import com.example.tfg.model.book.BookState
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.ListRepository
import com.example.tfg.ui.lists.listDetails.ListDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

class ListDetailsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ListDetailsViewModel
    private lateinit var listRepository: ListRepository
    private lateinit var listsState: ListsState
    private lateinit var bookState: BookState


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        listRepository = mockk()
        listsState = ListsState()
        bookState = BookState()

        var list = BookListClass(
            listId = "listId",
            listName = "listName",
            books = arrayListOf(Book("Search title"),Book("Other title")),
        )

        listsState.setDetailsList(list)
        coEvery { listRepository.getAllListInfo(any())} returns list

        viewModel = ListDetailsViewModel(
            listsState,
            listRepository,
            bookState
        )

        testDispatcher.scheduler.advanceUntilIdle()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search in list test with user query`(){
        assert(viewModel.listDetailsInfo.baseListOfBooks.size == 2)
        viewModel.listDetailsInfo = viewModel.listDetailsInfo.copy(userQuery = "Search title")

        viewModel.searchInList()
        assert(viewModel.listDetailsInfo.baseListOfBooks.size == 1)
        assert(viewModel.listDetailsInfo.baseListOfBooks[0].title == "Search title")
    }

    @Test
    fun `search in list test with user query empty`(){
        assert(viewModel.listDetailsInfo.baseListOfBooks.size == 2)
        viewModel.listDetailsInfo = viewModel.listDetailsInfo.copy(userQuery = "")

        viewModel.searchInList()
        assert(viewModel.listDetailsInfo.baseListOfBooks.size == 2)
    }

    @Test
    fun `change menu test`(){
        assert(!viewModel.listDetailsInfo.menuOpen)
        viewModel.changeMenu(true)
        assert(viewModel.listDetailsInfo.menuOpen)
    }

    @Test
    fun `toggle delete dialog test`() {
        assert(!viewModel.listDetailsInfo.deleteDialog)
        viewModel.toggleDeleteDialog()
        assert(viewModel.listDetailsInfo.deleteDialog)
    }

    @Test
    fun `set book for details test`() {
        val book = Book("title")
        val book2 = Book("Other title")
        bookState.bookForDetails = book2
        assert(bookState.bookForDetails != book)

        viewModel.setBookForDetails(book)
        assert(viewModel.bookState.bookForDetails == book)
    }

    @Test
    fun `delete list test`() {
        assert(!viewModel.listDetailsInfo.isDeleted)
        coEvery { listRepository.deleteList(any())} returns true


        viewModel.deleteList()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.listDetailsInfo.isDeleted)
        assert(listsState.getOwnLists().isEmpty())

    }


}