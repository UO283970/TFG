package com.example.tfg.ui.bookDetails.reviews

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.book.BookState
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.repository.ActivityRepository
import com.graphQL.type.UserActivityType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookReviewsState(
    val listOfReviews: ArrayList<ReviewActivity> = arrayListOf<ReviewActivity>(),
    val loadInfo: Boolean = false,
    val menuOpen: Boolean = false,
    val deleteDialogOpen: Boolean = false
)

@HiltViewModel
class ReviewsScreenViewModel @Inject constructor(
    val activityRepository: ActivityRepository,
    val bookState: BookState,
    val mainUserState: MainUserState
) : ViewModel() {

    var bookReviewState by mutableStateOf(BookReviewsState())

    init {
        getReviews()
    }

    fun toggleMenu() {
        bookReviewState = bookReviewState.copy(menuOpen = !bookReviewState.menuOpen)
    }

    fun toggleDeleteDialogOpen() {
        bookReviewState = bookReviewState.copy(menuOpen = !bookReviewState.deleteDialogOpen)
    }

    fun deleteReview(reviewActivity: ReviewActivity) {
        viewModelScope.launch {
            val reviews =
                activityRepository.deleteActivity(mainUserState.getMainUser()?.userId + "|" + bookState.bookForDetails.bookId + "|" + UserActivityType.REVIEW.toString())
            if (reviews != null) {
                bookState.bookForDetails.listOfReviews.remove(reviewActivity)
                bookReviewState = bookReviewState.copy(loadInfo = true)
            }
        }
    }

    private fun getReviews() {
        viewModelScope.launch {
            bookState.bookForDetails.listOfReviews = arrayListOf<ReviewActivity>()
            val reviews = activityRepository.getAllReviewsForBook(bookState.bookForDetails.bookId)
            if (reviews != null) {
                bookState.bookForDetails.listOfReviews.addAll(reviews)
                bookReviewState = bookReviewState.copy(loadInfo = true)
            }
        }

    }

}