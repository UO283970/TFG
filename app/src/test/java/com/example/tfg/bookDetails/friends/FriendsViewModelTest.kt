package com.example.tfg.bookDetails.friends

import androidx.lifecycle.SavedStateHandle
import com.example.tfg.model.book.Book
import com.example.tfg.model.book.BookState
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.RatingActivity
import com.example.tfg.repository.ActivityRepository
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.friends.FriendsViewModel
import com.graphQL.GetMinUserInfoQuery
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
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FriendsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: FriendsViewModel
    private lateinit var activityRepository: ActivityRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var userRepository: UserRepository
    private lateinit var bookState: BookState


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        activityRepository = mockk()
        savedStateHandle = SavedStateHandle()
        userRepository = mockk()
        bookState = BookState()

        coEvery { activityRepository.getAllFollowedActivity(any()) } returns listOf()
        coEvery { userRepository.getUserSearchInfo(any()) } returns listOf()
        coEvery { userRepository.getMinUserInfo() } returns GetMinUserInfoQuery.GetMinUserInfo("","","","")

        viewModel = FriendsViewModel(
            savedStateHandle,
            activityRepository,
            userRepository,
            bookState)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `expand search bar true and delete queries`(){
        viewModel.friendsInfo.value.queryResult = arrayListOf(User("",""))
        viewModel.friendsInfo.value.userQuery = "AnyQuery"

        viewModel.changeExpandedSearchBar(true)
        assert(viewModel.friendsInfo.value.expandedSearchBar)
        assertEquals(arrayListOf(),viewModel.friendsInfo.value.queryResult)
        assertEquals("",viewModel.friendsInfo.value.userQuery)
    }

    @Test
    fun `only expand search bar true`(){
        viewModel.onlyChangeExpandedSearchBar(true)
        assert(viewModel.friendsInfo.value.expandedSearchBar)
    }

    @Test
    fun `change userQuery`(){
        viewModel.friendsInfo.value.userQuery = "AnyQuery"
        assertEquals("AnyQuery",viewModel.friendsInfo.value.userQuery)
        viewModel.userFriendQueryChange("Changed")
        assertEquals("Changed",viewModel.friendsInfo.value.userQuery)
    }

    @Test
    fun `save state`(){
        assertNull(viewModel.friendsInfo.value.userSelected)
        assertEquals(false,viewModel.friendsInfo.value.userExpandedInfoLoaded)

        var user = User("userAlias",userId = "userId")

        viewModel.saveState(user)

        assertEquals(user,viewModel.friendsInfo.value.userSelected)
        assertEquals(true,viewModel.friendsInfo.value.userExpandedInfoLoaded)
    }

    @Test
    fun `changeExpandedInfoLoaded test`(){
        viewModel.changeExpandedInfoLoaded()
        assertEquals(false,viewModel.friendsInfo.value.userExpandedInfoLoaded)
    }

    @Test
    fun `search users no results`(){
        viewModel.friendsInfo.value.userQuery = "AnyQuery"
        viewModel.searchUsers()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `search users`(){
        assertEquals(0,viewModel.friendsInfo.value.queryResult.size)
        coEvery { userRepository.getUserSearchInfo(any()) } returns listOf(User("userAlias",userId = "userId"))
        viewModel.friendsInfo.value.userQuery = "AnyQuery"

        viewModel.searchUsers()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1,viewModel.friendsInfo.value.queryResult.size)
    }

    @Test
    fun `search users no user query`(){
        assertEquals(0,viewModel.friendsInfo.value.queryResult.size)
        viewModel.searchUsers()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(0,viewModel.friendsInfo.value.queryResult.size)
    }

    @Test
    fun `search users null return`(){
        assertEquals(0,viewModel.friendsInfo.value.queryResult.size)
        viewModel.friendsInfo.value.userQuery = "AnyQuery"
        coEvery { userRepository.getUserSearchInfo(any()) } returns null

        viewModel.searchUsers()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(0,viewModel.friendsInfo.value.queryResult.size)
    }

    @Test
    fun `refresh activities test`(){
        viewModel.friendsInfo.value.followedActivity = arrayListOf()
        coEvery { activityRepository.getAllFollowedActivity(any()) } returns null
        viewModel.friendsInfo.value.activityLoaded = false
        viewModel.friendsInfo.value.isRefreshing = false
        viewModel.refreshActivities()

        testDispatcher.scheduler.advanceUntilIdle()
        assert(viewModel.friendsInfo.value.isRefreshing)
    }

    @Test
    fun `get followed activity`(){
        viewModel.friendsInfo.value.followedActivity = arrayListOf()
        coEvery { activityRepository.getAllFollowedActivity(any()) } returns listOf()
        viewModel.friendsInfo.value.activityLoaded = false
        viewModel.getFollowedActivity()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.friendsInfo.value.activityLoaded)
        assertEquals(0,viewModel.friendsInfo.value.followedActivity.size)
    }

    @Test
    fun `get followed activity null`(){
        viewModel.friendsInfo.value.followedActivity = arrayListOf()
        coEvery { activityRepository.getAllFollowedActivity(any()) } returns null
        viewModel.friendsInfo.value.activityLoaded = false
        viewModel.getFollowedActivity()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `get followed activity not an empty list`(){
        viewModel.friendsInfo.value.followedActivity = arrayListOf(RatingActivity(
            user = User(""),
            creationDate = LocalDate.now(),
            book = Book("", ""),
            rating = 5,
            timeStamp = "timeStamp"
        ))
        coEvery { activityRepository.getAllFollowedActivity(any()) } returns listOf()
        viewModel.friendsInfo.value.activityLoaded = false
        viewModel.getFollowedActivity()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.friendsInfo.value.activityLoaded)
        assertEquals(0,viewModel.friendsInfo.value.followedActivity.size)
    }

    @Test
    fun `get followed activity refresh test`(){
        viewModel.friendsInfo.value.followedActivity = arrayListOf()
        coEvery { activityRepository.getAllFollowedActivity(any()) } returns listOf()
        viewModel.friendsInfo.value.activityLoaded = false
        viewModel.getFollowedActivityRefresh()

        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.friendsInfo.value.activityLoaded)
        assertEquals(0,viewModel.friendsInfo.value.followedActivity.size)
    }

    @Test
    fun `get followed activity refresh not an empty list`(){
        viewModel.friendsInfo.value.followedActivity = arrayListOf(RatingActivity(
            user = User(""),
            creationDate = LocalDate.now(),
            book = Book("", ""),
            rating = 5,
            timeStamp = "timeStamp"
        ))
        coEvery { activityRepository.getAllFollowedActivity(any()) } returns listOf()
        viewModel.friendsInfo.value.activityLoaded = false
        viewModel.getFollowedActivityRefresh()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.friendsInfo.value.activityLoaded)
        assertEquals(1,viewModel.friendsInfo.value.followedActivity.size)
    }
}