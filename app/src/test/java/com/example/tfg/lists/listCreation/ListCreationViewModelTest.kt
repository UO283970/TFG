package com.example.tfg.lists.listCreation

import androidx.lifecycle.SavedStateHandle
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.ListPrivacy
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.ListRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.lists.listCreation.ListCreationViewModel
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
import kotlin.test.assertEquals

class ListCreationViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ListCreationViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var stringResourcesProvider: StringResourcesProvider
    private lateinit var listRepository: ListRepository
    private lateinit var listsState: ListsState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        savedStateHandle = SavedStateHandle()
        stringResourcesProvider = mockk()
        listRepository = mockk()
        listsState = ListsState()

        viewModel = ListCreationViewModel(
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
        assertEquals("name",viewModel.listCreationState.listName)
        viewModel.changeListName("newName")
        assertEquals("newName",viewModel.listCreationState.listName)
    }

    @Test
    fun `change list desc test`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listDescription = "desc")
        assertEquals("desc",viewModel.listCreationState.listDescription)
        viewModel.changeListDesc("newDesc")
        assertEquals("newDesc",viewModel.listCreationState.listDescription)
    }

    @Test
    fun `change list privacy test`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listPrivacy = ListPrivacy.PUBLIC)
        assertEquals(ListPrivacy.PUBLIC,viewModel.listCreationState.listPrivacy)
        viewModel.changeListPrivacy(ListPrivacy.PRIVATE)
        assertEquals(ListPrivacy.PRIVATE,viewModel.listCreationState.listPrivacy)
    }

    @Test
    fun `change drop down state test`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(dropDawnExpanded = true)
        assertEquals(true,viewModel.listCreationState.dropDawnExpanded)
        viewModel.changeDropDownState(false)
        assertEquals(false,viewModel.listCreationState.dropDawnExpanded)
    }

    @Test
    fun `save a new list name incorrect test`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listName = "")
        every { stringResourcesProvider.getString(any()) } returns "error"

        viewModel.saveNewList()
        assert(viewModel.listCreationState.listNameError != null)

    }

    @Test
    fun `save a new list name duplicated test`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listName = "listName")
        listsState.setOwnList(arrayListOf(BookListClass(
            listId = "listId",
            listName = "listName",
        )))
        every { stringResourcesProvider.getString(any()) } returns "error"

        viewModel.saveNewList()
        assert(viewModel.listCreationState.listNameError != null)

    }

    @Test
    fun `save a new list test`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listName = "listName")
        coEvery { listRepository.createList(any(),any(),any()) } returns ""

        viewModel.saveNewList()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.listCreationState.listNameError == null)
        assert(viewModel.listCreationState.listCreated)
        assertEquals("listName",listsState.getOwnLists()[0].getName())
    }

    @Test
    fun `save a new list null return test`(){
        viewModel.listCreationState = viewModel.listCreationState.copy(listName = "listName")
        coEvery { listRepository.createList(any(),any(),any()) } returns null
        every { stringResourcesProvider.getString(any()) } returns "error"

        viewModel.saveNewList()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.listCreationState.listNameError != null)
        assert(listsState.getOwnLists().isEmpty())
    }
}