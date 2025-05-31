package com.example.tfg.bookDetails.reviews

import com.example.tfg.model.book.Book
import com.example.tfg.model.book.BookState
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.repository.ActivityRepository
import com.example.tfg.ui.bookDetails.reviews.ReviewsScreenViewModel
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

class ReviewsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ReviewsScreenViewModel
    private lateinit var activityRepository: ActivityRepository
    private lateinit var bookState: BookState
    private lateinit var mainUserState: MainUserState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        activityRepository = mockk()
        bookState = BookState()
        mainUserState = MainUserState()

        viewModel = ReviewsScreenViewModel(
            activityRepository,
            bookState,
            mainUserState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `toggle menu`(){
        val initial = viewModel.bookReviewState.menuOpen
        viewModel.toggleMenu()
        assertEquals(!initial,viewModel.bookReviewState.menuOpen)
    }

    @Test
    fun `toggle delete dialog`(){
        val initial = viewModel.bookReviewState.deleteDialogOpen
        viewModel.toggleDeleteDialogOpen()
        assertEquals(!initial,viewModel.bookReviewState.deleteDialogOpen)
    }

    @Test
    fun `delete a review`(){
        var book = Book("Title", "Author")

        val review = ReviewActivity(
            user = User(""),
            creationDate = LocalDate.now(),
            book = book,
            reviewText = "Text",
            rating = 5,
            timeStamp = "Timestamp"
        )

        book.listOfReviews = arrayListOf<ReviewActivity>(review)
        bookState.bookForDetails = book

        coEvery { activityRepository.deleteActivity(any())} returns null
        viewModel.deleteReview(review)

        assert(!bookState.bookForDetails.listOfReviews.isEmpty())

        coEvery { activityRepository.deleteActivity(any())} returns true

        viewModel.deleteReview(review)
        testDispatcher.scheduler.advanceUntilIdle()

        assert(bookState.bookForDetails.listOfReviews.isEmpty())

    }
}