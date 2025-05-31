package com.example.tfg.home.notifications.followRequest

import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.User
import com.example.tfg.repository.FollowRepository
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.home.notifications.followRequest.FollowRequestViewModel
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
import kotlin.test.assertEquals

class FollowRequestViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: FollowRequestViewModel
    private lateinit var followRepository: FollowRepository
    private lateinit var userRepository: UserRepository
    private lateinit var mainUserState: MainUserState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        followRepository = mockk()
        userRepository = mockk()
        mainUserState = MainUserState()

        val user = User("userAlias",userId = "userId")
        mainUserState.setMainUser(user)

        viewModel = FollowRequestViewModel(
            userRepository,
            followRepository,
            mainUserState
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `accept friend request test true result`(){
        val user = User("userAlias",userId = "userId")

        viewModel.requestMainState.copy( friendRequests = arrayListOf(user))

        coEvery { followRepository.acceptRequest(any()) } returns true
        coEvery { userRepository.getUserFollowRequest() } returns arrayListOf(user)
        viewModel.acceptFriendRequest(user)

        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.requestMainState.friendRequests.isEmpty())
        assertEquals(1,mainUserState.getMainUser()?.followers)
    }

    @Test
    fun `accept friend request test false result`(){
        val user = User("userAlias",userId = "userId")

        viewModel.requestMainState.copy( friendRequests = arrayListOf(user))

        coEvery { followRepository.acceptRequest(any()) } returns false

        viewModel = FollowRequestViewModel(
            userRepository,
            followRepository,
            mainUserState
        )

        viewModel.acceptFriendRequest(user)

        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.requestMainState.friendRequests.isEmpty())
        assert(mainUserState.getMainUser()?.followers == 0)
    }

    @Test
    fun `delete friend request test true result`(){
        val user = User("userAlias",userId = "userId")
        viewModel.requestMainState.copy( friendRequests = arrayListOf(user))
        coEvery { followRepository.cancelRequest(any()) } returns true
        viewModel.deleteFriendRequest(user)

        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.requestMainState.friendRequests.isEmpty())
    }

}