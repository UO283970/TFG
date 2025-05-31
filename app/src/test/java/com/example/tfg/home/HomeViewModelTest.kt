package com.example.tfg.home

import androidx.lifecycle.SavedStateHandle
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.model.notifications.FollowNotification
import com.example.tfg.model.notifications.NotificationTypes
import com.example.tfg.model.user.User
import com.example.tfg.repository.ListRepository
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.home.HomeViewModel
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
import kotlin.test.assertEquals

class HomeViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var listRepository: ListRepository
    private lateinit var listsState: ListsState
    private lateinit var userRepository: UserRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        savedStateHandle = SavedStateHandle()
        userRepository = mockk()
        listRepository = mockk()
        listsState = ListsState()

        coEvery { userRepository.getUserNotifications(any()) } returns listOf(FollowNotification(
            user = User("",""),
            notificationId = "notificationId",
            timeStamp = "timeStamp",
            notificationType = NotificationTypes.FOLLOW
        ))
        coEvery { listRepository.getBasicListInfo(any()) } returns listOf()
        coEvery { listRepository.getUserDefaultLists(any()) } returns listOf(DefaultList(
            listId = "listId",
            listName = "listName",
        ))

        viewModel = HomeViewModel(
            savedStateHandle,
            listRepository,
            listsState,
            userRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `go to reading`(){
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.goToReading()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listsState.getDefaultLists()[0],listsState.getDetailList())
    }

    @Test
    fun `init with no notifications and not empty lists`(){
        coEvery { userRepository.getUserNotifications(any()) } returns listOf()
        listsState.setDefaultList(arrayListOf<DefaultList>(DefaultList(
            listId = "listId",
            listName = "listName",
        )))

        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.goToReading()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listsState.getDefaultLists()[0],listsState.getDetailList())
    }

}