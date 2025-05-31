package com.example.tfg.profile.statistics.reviews

import androidx.lifecycle.SavedStateHandle
import com.example.tfg.model.book.Book
import com.example.tfg.model.book.BookState
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.profile.components.statistics.reviews.ReviewsScreenViewModel
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

class ReviewsScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ReviewsScreenViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var userRepository: UserRepository
    private lateinit var bookState: BookState



    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        userRepository = mockk()
        savedStateHandle = SavedStateHandle()
        bookState = BookState()

        coEvery { userRepository.getUsersReviews(any()) } returns listOf(ReviewActivity(
            user = User(""),
            creationDate = LocalDate.now(),
            book = Book(),
            reviewText = "Text",
            rating = 5,
            timeStamp = "timeStamp"
        ))

        viewModel = ReviewsScreenViewModel(
            savedStateHandle,
            userRepository,
            bookState
        )

        testDispatcher.scheduler.advanceUntilIdle()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `set book for detail test`(){
        val book = Book(bookId = "bookId")
        viewModel.setBookDetails(book)

        assert(viewModel.bookState.bookForDetails == book)
    }

    @Test
    fun `get user reviews test`(){
        viewModel.getUserReviews()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(!viewModel.profileReviewsInfo.profileReviews.isEmpty())

    }
}