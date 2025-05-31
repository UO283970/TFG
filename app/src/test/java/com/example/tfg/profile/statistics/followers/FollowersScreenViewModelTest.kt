package com.example.tfg.profile.statistics.followers

import androidx.lifecycle.SavedStateHandle
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.repository.FollowRepository
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.profile.components.statistics.followers.FollowersScreenViewModel
import com.graphQL.type.UserFollowState
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

class FollowersScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: FollowersScreenViewModel
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
        coEvery { userRepository.getFollowersOfUser(any()) } returns listOf(User("userAlias", userId = "userId", userName = "userName"))

        viewModel = FollowersScreenViewModel(
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
        assert(!viewModel.followersInfo.deleteDialog)
        viewModel.changeOpenDialog()
        assert(viewModel.followersInfo.deleteDialog)
    }

    @Test
    fun `change to not following test`() {
        val user = User("userAlias", userId = "userId", followState = UserFollowStateEnum.FOLLOWING)

        assert(mainUserState.getMainUser()?.following == 1)
        coEvery { followRepository.cancelFollow(any()) } returns true
        coEvery { userRepository.getFollowersOfUser(any()) } returns listOf(user)

        viewModel.changeToNotFollowing(user)
        testDispatcher.scheduler.advanceUntilIdle()

        assert(mainUserState.getMainUser()?.following == 0)

    }

    @Test
    fun `change to not following test not following`() {
        val user = User("userAlias", userId = "userId", followState = UserFollowStateEnum.REQUESTED)

        assert(mainUserState.getMainUser()?.following == 1)
        coEvery { userRepository.getFollowersOfUser(any()) } returns listOf(user)
        coEvery { followRepository.cancelFollow(any()) } returns true

        viewModel.changeToNotFollowing(user)
        testDispatcher.scheduler.advanceUntilIdle()

        assert(mainUserState.getMainUser()?.following == 1)
    }

    @Test
    fun `follow user test to follow`(){
        assert(mainUserState.getMainUser()?.following == 1)
        coEvery { followRepository.followUser(any()) } returns UserFollowState.FOLLOWING

        viewModel.followUser(User(""))
        testDispatcher.scheduler.advanceUntilIdle()

        assert(mainUserState.getMainUser()?.following == 2)
    }

    @Test
    fun `follow user test to requested`(){
        assert(mainUserState.getMainUser()?.following == 1)
        coEvery { followRepository.followUser(any()) } returns UserFollowState.REQUESTED

        viewModel.followUser(User(""))
        testDispatcher.scheduler.advanceUntilIdle()

        assert(mainUserState.getMainUser()?.following == 1)
    }

    @Test
    fun `delete follower test`(){
        assert(mainUserState.getMainUser()?.followers == 1)
        coEvery { followRepository.deleteFromFollower(any()) } returns true

        viewModel.deleteFollower(User("userAlias", userId = "userId", userName = "userName"))
        testDispatcher.scheduler.advanceUntilIdle()

        assert(mainUserState.getMainUser()?.followers == 0)
    }
}