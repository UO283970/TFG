package com.example.tfg.profile

import com.example.tfg.model.book.Book
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.User
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.profile.ProfileViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProfileViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ProfileViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var listsState: ListsState
    private lateinit var mainUserState: MainUserState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        userRepository = mockk()
        listsState = ListsState()
        mainUserState = MainUserState()

        coEvery { userRepository.getAuthenticatedUserInfo() } returns User("otherUserAlias", userId = "userId")

        viewModel = ProfileViewModel(
            userRepository,
            mainUserState,
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
    fun `set list for details test`() {
        var list = BookListClass(
            listId = "listId",
            listName = "listName",
            books = arrayListOf(Book("Search title"), Book("Other title")),
        )

        viewModel.listDetails(list)
        assert(listsState.getDetailList() == list)
    }

    @Test
    fun `refresh profile info test`(){

        viewModel.refreshProfile()
        testDispatcher.scheduler.advanceUntilIdle()


    }
}