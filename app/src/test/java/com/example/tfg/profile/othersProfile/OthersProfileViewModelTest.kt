package com.example.tfg.profile.othersProfile

import androidx.lifecycle.SavedStateHandle
import com.example.tfg.model.book.Book
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.repository.FollowRepository
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.profile.othersProfile.OthersProfileViewModel
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
import kotlin.test.Test

class OthersProfileViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: OthersProfileViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var userRepository: UserRepository
    private lateinit var listsState: ListsState
    private lateinit var followRepository: FollowRepository
    private lateinit var mainUserState: MainUserState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        savedStateHandle = SavedStateHandle()
        userRepository = mockk()
        listsState = ListsState()
        followRepository = mockk()
        mainUserState = MainUserState()

        coEvery { userRepository.getAllUserInfo(any()) } returns User("otherUserAlias", userId = "userId2", followState = UserFollowStateEnum.FOLLOWING)
        mainUserState.setMainUser(User("userAlias", userId = "userId", following = 1))

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
        coEvery { userRepository.getAllUserInfo(any()) } returns User("otherUserAlias", userId = "userId2", followState = UserFollowStateEnum.FOLLOWING)

        viewModel = OthersProfileViewModel(
            listsState,
            userRepository,
            followRepository,
            mainUserState,
            savedStateHandle
        )

        viewModel.listDetails(list)
        assert(listsState.getDetailList() == list)
    }

    @Test
    fun `change to not following test`() {
        assert(mainUserState.getMainUser()?.following == 1)
        coEvery { followRepository.cancelFollow(any()) } returns true

        coEvery { userRepository.getAllUserInfo(any()) } returns User("otherUserAlias", userId = "userId2", followState = UserFollowStateEnum.FOLLOWING)

        viewModel = OthersProfileViewModel(
            listsState,
            userRepository,
            followRepository,
            mainUserState,
            savedStateHandle
        )


        viewModel.changeToNotFollowing()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(mainUserState.getMainUser()?.following == 0)
        assert(viewModel.profileInfo.value.userInfoLoaded)

    }

    @Test
    fun `change to not following test not following`() {
        assert(mainUserState.getMainUser()?.following == 1)
        coEvery { userRepository.getAllUserInfo(any()) } returns User("otherUserAlias", userId = "userId2", followState = UserFollowStateEnum.REQUESTED)
        coEvery { followRepository.cancelFollow(any()) } returns true

        viewModel = OthersProfileViewModel(
            listsState,
            userRepository,
            followRepository,
            mainUserState,
            savedStateHandle
        )

        viewModel.changeToNotFollowing()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(mainUserState.getMainUser()?.following == 1)
        assert(viewModel.profileInfo.value.userInfoLoaded)
    }

    @Test
    fun `follow user test`(){
        assert(mainUserState.getMainUser()?.following == 1)
        coEvery { followRepository.followUser(any()) } returns UserFollowState.FOLLOWING
        coEvery { userRepository.getAllUserInfo(any()) } returns User("otherUserAlias", userId = "userId2", followState = UserFollowStateEnum.NOT_FOLLOW)

        viewModel = OthersProfileViewModel(
            listsState,
            userRepository,
            followRepository,
            mainUserState,
            savedStateHandle
        )

        viewModel.followUser()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(mainUserState.getMainUser()?.following == 2)
    }

    @Test
    fun `refresh user info test`(){
        coEvery { userRepository.getAllUserInfo(any()) } returns User("otherUserAlias", userId = "userId2", followState = UserFollowStateEnum.FOLLOWING)

        viewModel = OthersProfileViewModel(
            listsState,
            userRepository,
            followRepository,
            mainUserState,
            savedStateHandle
        )

        viewModel.refreshUser()
        testDispatcher.scheduler.advanceUntilIdle()
    }
}