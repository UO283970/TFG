package com.example.tfg.lists.listModify

import androidx.lifecycle.SavedStateHandle
import com.example.tfg.model.book.Book
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.ListPrivacy
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.ListRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.lists.listModify.ListModifyViewModel
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
import org.junit.Test

class ListModifyViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ListModifyViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var stringResourcesProvider: StringResourcesProvider
    private lateinit var listRepository: ListRepository
    private lateinit var listsState: ListsState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        savedStateHandle = SavedStateHandle()
        stringResourcesProvider = StringResourcesProvider(mockk())
        listRepository = mockk()
        listsState = ListsState()


        var list = BookListClass(
            listId = "listId",
            listName = "listName",
            books = arrayListOf(Book("Search title"),Book("Other title")),
        )
        listsState.setDetailsList(list)

        viewModel = ListModifyViewModel(
            savedStateHandle,
            stringResourcesProvider,
            listRepository,
            listsState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `change list name test`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listName = "name")
        assert(viewModel.listCreationState.listName == "name")

        viewModel.changeListName("newName")
        assert(viewModel.listCreationState.listName == "newName")
    }

    @Test
    fun `change list description test`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listDescription = "desc")
        assert(viewModel.listCreationState.listDescription == "desc")

        viewModel.changeListDesc("newDesc")
        assert(viewModel.listCreationState.listDescription == "newDesc")
    }

    @Test
    fun `change list privacy test`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listPrivacy = ListPrivacy.PUBLIC)
        assert(viewModel.listCreationState.listPrivacy == ListPrivacy.PUBLIC)

        viewModel.changeListPrivacy(ListPrivacy.PRIVATE)
        assert(viewModel.listCreationState.listPrivacy == ListPrivacy.PRIVATE)
    }

    @Test
    fun `change drop down menu state test`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(dropDawnExpanded = true)
        assert(viewModel.listCreationState.dropDawnExpanded)

        viewModel.changeDropDownState(false)
        assert(!viewModel.listCreationState.dropDawnExpanded)
    }

    @Test
    fun `save new list test with incorrect name`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listName = "")
        assert(viewModel.listCreationState.listNameError == null)
        every { stringResourcesProvider.getString(any()) } returns "error"

        viewModel.saveNewList()
        assert(viewModel.listCreationState.listNameError != null)
    }


    @Test
    fun `save new list test with duplicated name`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listName = "listName")
        listsState.setOwnList(arrayListOf(BookListClass(
            listId = "listId2",
            listName = "listName",
        )))
        assert(viewModel.listCreationState.listNameError == null)
        every { stringResourcesProvider.getString(any()) } returns "error"

        viewModel.saveNewList()
        assert(viewModel.listCreationState.listNameError != null)
    }

    @Test
    fun `save new list test with correct name`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listName = "newListName")
        coEvery { listRepository.updateList(any(),any(),any(),any()) } returns true
        listsState.setOwnList(arrayListOf(BookListClass(
            listId = "listId",
            listName = "listName",
        )))

        viewModel.saveNewList()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.listCreationState.listNameError == null)
        assert(viewModel.listCreationState.listModify)
    }

    @Test
    fun `save new list test with null return`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listName = "newListName")
        coEvery { listRepository.updateList(any(),any(),any(),any()) } returns null
        every { stringResourcesProvider.getString(any()) } returns "error"

        viewModel.saveNewList()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.listCreationState.listNameError != null)
    }
}