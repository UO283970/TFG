package com.example.tfg.profile.statistics.follows

import androidx.lifecycle.SavedStateHandle
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.repository.FollowRepository
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.profile.components.statistics.follows.FollowsScreenViewModel
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

class FollowsScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: FollowsScreenViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var userRepository: UserRepository
    private lateinit var followRepository: FollowRepository
    private lateinit var mainUserState: MainUserState


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        userRepository = mockk()
        followRepository = mockk()
        mainUserState = MainUserState()
        savedStateHandle = SavedStateHandle()

        mainUserState.setMainUser(User("userAlias", userId = "userId", following = 1, followers = 1))
        coEvery { userRepository.getFollowingListUser(any()) } returns listOf(User("userAlias", userId = "userId", userName = "userName"))

        viewModel = FollowsScreenViewModel(
            savedStateHandle,
            userRepository,
            followRepository,
            mainUserState
        )

        testDispatcher.scheduler.advanceUntilIdle()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `change open dialog test`(){
        assert(!viewModel.followsInfo.deleteDialog)
        viewModel.changeOpenDialog()
        assert(viewModel.followsInfo.deleteDialog)
    }

    @Test
    fun `change to not following test`() {
        val user = User("otherUserAlias", userId = "userId2", followState = UserFollowStateEnum.FOLLOWING)

        assert(mainUserState.getMainUser()?.following == 1)
        coEvery { followRepository.cancelFollow(any()) } returns true

        viewModel.deleteFollow(user)
        testDispatcher.scheduler.advanceUntilIdle()

        assert(mainUserState.getMainUser()?.following == 0)
        assert(user.followState == UserFollowStateEnum.NOT_FOLLOW)

    }

}