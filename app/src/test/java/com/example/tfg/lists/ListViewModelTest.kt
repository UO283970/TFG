package com.example.tfg.lists

import androidx.lifecycle.SavedStateHandle
import com.example.tfg.model.book.Book
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.ListRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.lists.ListViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

class ListViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ListViewModel
    private lateinit var stringResourcesProvider: StringResourcesProvider
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var listRepository: ListRepository
    private lateinit var listsState: ListsState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        stringResourcesProvider = mockk()
        savedStateHandle = SavedStateHandle()
        listRepository = mockk()
        listsState = ListsState()

        var list = BookListClass(
            listId = "listId",
            listName = "listName",
            books = arrayListOf(Book("Search title"),Book("Other title")),
        )
        var list2 = BookListClass(
            listId = "listId2",
            listName = "Other List name",
            books = arrayListOf(Book("Search title"),Book("Other title")),
        )
        listsState.setOwnList(arrayListOf(list,list2))

        var defList = DefaultList(
            listId = "listId",
            listName = "Reading",
            books = arrayListOf(Book("Search title"),Book("Other title")),
        )
        var defList2 = DefaultList(
            listId = "listId",
            listName = "Read",
            books = arrayListOf(Book("Search title"),Book("Other title")),
        )


        every { stringResourcesProvider.getString(any()) } returns ""
        every { stringResourcesProvider.getStringArray(any()) } returns listOf("any")
        coEvery { listRepository.getUserDefaultLists(any())} returns listOf(defList,defList2)

        viewModel = ListViewModel(
            stringResourcesProvider,
            savedStateHandle,
            listRepository,
            listsState
        )

        testDispatcher.scheduler.advanceUntilIdle()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `user query change test`(){
        viewModel.userQueryChange("AnyQuery")
        assert(viewModel.listState.value.userQuery == "AnyQuery")

        viewModel.userQueryChange("NewQuery")
        assert(viewModel.listState.value.userQuery == "NewQuery")
    }

    @Test
    fun `tab change test`(){
        assert(viewModel.listState.value.tabIndex == 0)

        viewModel.tabChange(1)
        assert(viewModel.listState.value.tabIndex == 1)

        viewModel.tabChange(0)
        assert(viewModel.listState.value.tabIndex == 0)
    }

    @Test
    fun `list details test`(){
        val list = BookListClass(
            listId = "listId",
            listName = "listName",
            books = arrayListOf(Book("Search title"),Book("Other title")),
        )
        viewModel.listDetails(list)
        assert(listsState.getDetailList() == list)
    }

    @Test
    fun `list creation test`(){
        listsState.setDetailsList(BookListClass("",""))
        assert(viewModel.listCreation() == "listCreation")
    }

    @Test
    fun `search list test own lists`(){
        assert(viewModel.listState.value.ownLists.size == 2)
        viewModel.userQueryChange("listName")

        viewModel.searchList()
        assert(viewModel.listState.value.ownLists.size == 1)
        assert(viewModel.listState.value.ownLists[0].listName == "listName")

    }

    @Test
    fun `search list test default lists`(){
        viewModel.tabChange(1)
        assert(viewModel.listState.value.defaultLists.size == 2)
        viewModel.userQueryChange("Reading")

        viewModel.searchList()
        assert(viewModel.listState.value.defaultLists.size == 1)
        assert(viewModel.listState.value.defaultLists[0].listName == "Reading")

    }

    @Test
    fun `search list test no user input`(){
        assert(viewModel.listState.value.defaultLists.size == 2)
        viewModel.userQueryChange("")

        viewModel.searchList()
        assert(viewModel.listState.value.defaultLists.size == 2)

    }
}