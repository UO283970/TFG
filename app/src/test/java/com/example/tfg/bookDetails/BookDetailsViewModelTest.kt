package com.example.tfg.bookDetails

import com.example.tfg.R
import com.example.tfg.model.book.Book
import com.example.tfg.model.book.BookState
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.model.user.MainUserState
import com.example.tfg.repository.ActivityRepository
import com.example.tfg.repository.BookRepository
import com.example.tfg.repository.ListRepository
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.bookDetails.BookDetailsViewModel
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.GetExtraInfoForBookQuery
import com.graphQL.GetMinUserInfoQuery
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class BookDetailsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: BookDetailsViewModel
    private lateinit var stringResourcesProvider: StringResourcesProvider
    private lateinit var listRepository: ListRepository
    private lateinit var bookRepository: BookRepository
    private lateinit var userRepository: UserRepository
    private lateinit var activityRepository: ActivityRepository
    private lateinit var mainUserState: MainUserState
    private lateinit var listsState: ListsState
    private lateinit var bookState: BookState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        bookRepository = mockk()
        listRepository = mockk()
        userRepository = mockk()
        activityRepository = mockk()
        stringResourcesProvider = StringResourcesProvider(mockk())
        listsState = ListsState()
        bookState = BookState()
        mainUserState = MainUserState()


        var book = Book("Title", "Author", readingState = "Reading")
        bookState.bookForDetails = book
        every { stringResourcesProvider.getString(any()) } returns ""
        coEvery { bookRepository.getExtraInfoForBook(any()) } returns GetExtraInfoForBookQuery.GetExtraInfoForBook(0.0, 1, 1, listOf(), 0, 0)
        coEvery { userRepository.getMinUserInfo() } returns GetMinUserInfoQuery.GetMinUserInfo("","","","")

        viewModel = BookDetailsViewModel(
            stringResourcesProvider,
            listRepository,
            bookRepository,
            userRepository,
            activityRepository,
            mainUserState,
            listsState,
            bookState
        )

        viewModel.bookInfo = viewModel.bookInfo.copy(
            userScore = 4,
            deleted = false,
            refresh = false
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `change the user score`() {
        viewModel.changeUserScore(5)
        assertEquals(5, viewModel.bookInfo.userScore)
    }

    @Test
    fun `openAddList test`() {
        val initial = viewModel.bookInfo.addToListOpen
        viewModel.openAddList()
        assertEquals(!initial, viewModel.bookInfo.addToListOpen)
    }

    @Test
    fun `toggleRatingMenu test`() {
        val initial = viewModel.bookInfo.ratingMenuOpen
        viewModel.toggleRatingMenu()
        assertEquals(!initial, viewModel.bookInfo.ratingMenuOpen)
    }

    @Test
    fun `finishLoad test`() {
        viewModel.finishLoad()
        assertEquals(false, viewModel.bookInfo.loadInfo)
    }

    @Test
    fun `changeDeleted test`() {
        viewModel.changeDeleted(true)
        assert(viewModel.bookInfo.deleted)
        viewModel.changeDeleted(false)
        assertEquals(false, viewModel.bookInfo.deleted)
    }

    @Test
    fun `saveChanges test`() {
        val listName = "Read"
        val defaultButton = "Add to list"
        var list = DefaultList("listId", listName)

        viewModel.saveChanges(list, true)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(listName, viewModel.bookInfo.inListButtonString)

        every { stringResourcesProvider.getString(R.string.read_list_name) } returns listName
        every { stringResourcesProvider.getString(R.string.book_add_to_list) } returns defaultButton


        viewModel.saveChanges(list, true)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(listName, viewModel.bookInfo.inListButtonString)

        viewModel.saveChanges(list, false)
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(defaultButton, viewModel.bookInfo.inListButtonString)
        assertEquals("", viewModel.bookInfo.selectedBookList)
    }

    @Test
    fun `saveRating test same score`() {
        bookState.bookForDetails.userScore = 5
        bookState.bookForDetails.totalRatings = 1
        viewModel.changeUserScore(5)

        viewModel.saveRating()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, viewModel.bookState.bookForDetails.totalRatings)
    }

    @Test
    fun `saveRating test null return`() {
        bookState.bookForDetails.userScore = 5
        bookState.bookForDetails.totalRatings = 1
        bookState.bookForDetails.readingState = ""
        viewModel.changeUserScore(7)

        coEvery { activityRepository.addActivity(any(), any(), any(), any()) } returns null

        viewModel.saveRating()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, viewModel.bookState.bookForDetails.totalRatings)

    }

    @Test
    fun `saveRating test previous score 0`() {
        bookState.bookForDetails.userScore = 0
        bookState.bookForDetails.totalRatings = 0
        bookState.bookForDetails.readingState = ""
        viewModel.changeUserScore(7)

        coEvery { listRepository.addBookToDefaultList(any(), any()) } returns null
        every { stringResourcesProvider.getString(R.string.reading_list_name) } returns "Reading"
        coEvery { activityRepository.addActivity(any(), any(), any(), any()) } returns true

        viewModel.saveRating()
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(2, bookState.bookForDetails.totalRatings)
        assertEquals(7, bookState.bookForDetails.userScore)
        assertEquals("Reading", viewModel.bookInfo.inListButtonString)

    }

    @Test
    fun `saveRating test previous score not 0 and delete and in list`() {
        coEvery { bookRepository.getExtraInfoForBook(any()) } returns GetExtraInfoForBookQuery.GetExtraInfoForBook(0.0, 1, 1, listOf(), 1, 0)
        bookState.bookForDetails.readingState = "Read"
        viewModel.bookInfo = viewModel.bookInfo.copy(
            userScore = 7,
            deleted = true
        )

        coEvery { listRepository.addBookToDefaultList(any(), any()) } returns null
        every { stringResourcesProvider.getString(R.string.reading_list_name) } returns "Reading"
        coEvery { activityRepository.addActivity(any(), any(), any(), any()) } returns true

        viewModel.saveRating()
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(0, bookState.bookForDetails.totalRatings)
        assertEquals(0, bookState.bookForDetails.userScore)

    }
}