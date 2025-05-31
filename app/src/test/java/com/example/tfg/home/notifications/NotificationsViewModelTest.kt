package com.example.tfg.home.notifications

import com.example.tfg.model.notifications.FollowNotification
import com.example.tfg.model.notifications.NotificationTypes
import com.example.tfg.model.user.User
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.home.notifications.NotificationsMainState
import com.example.tfg.ui.home.notifications.NotificationsViewModel
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

class NotificationsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: NotificationsViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var notificationsMainState: NotificationsMainState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        userRepository = mockk()
        notificationsMainState = NotificationsMainState()
        coEvery { userRepository.getUserNotifications(any()) } returns listOf()

        viewModel = NotificationsViewModel(userRepository)

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `delete notification`(){
        val notification = FollowNotification(
            user = User("",""),
            notificationId = "notificationId",
            timeStamp = "timeStamp",
            notificationType = NotificationTypes.FOLLOWED
        )

        viewModel.notificationsMainState.copy(notificationList = arrayListOf(notification))
        coEvery { userRepository.deleteNotification(any()) } returns true

        viewModel.deleteNotification(notification)
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.notificationsMainState.notificationList.isEmpty())
    }

    @Test
    fun `get user notifications test with list not empty`(){
        val notification = FollowNotification(
            user = User("",""),
            notificationId = "notificationId",
            timeStamp = "timeStamp",
            notificationType = NotificationTypes.FOLLOWED)

        viewModel.notificationsMainState.copy(notificationList = arrayListOf(notification))
        coEvery { userRepository.getUserNotifications(any()) } returns listOf(notification)

        viewModel.refreshNotifications()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.notificationsMainState.notificationList.isNotEmpty())
    }
}