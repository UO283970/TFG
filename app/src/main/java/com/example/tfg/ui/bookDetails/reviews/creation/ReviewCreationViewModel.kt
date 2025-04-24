package com.example.tfg.ui.bookDetails.reviews.creation

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
import java.time.LocalDate
import javax.inject.Inject

data class ReviewCreationState(
    val switchState: Boolean = false,
    val reviewCreated: Boolean = false,
    val reviewText: String = "",
    var ratingMenuOpen: Boolean = false,
    var userScore: Int,
    var deleted: Boolean = false,
    var refresh: Boolean = true
)

@HiltViewModel
class ReviewCreationViewModel @Inject constructor(val activityRepository: ActivityRepository, val bookState: BookState, val mainUserState: MainUserState) :
    ViewModel() {

    var creationState by mutableStateOf(ReviewCreationState(userScore = bookState.bookForDetails.userScore,))

    fun changeSwitch() {
        creationState = creationState.copy(switchState = !creationState.switchState)
    }

    fun changeReviewText(text: String) {
        creationState = creationState.copy(reviewText = text)
    }

    fun changeUserScore(score: Int){
        creationState = creationState.copy(userScore = score)
    }

    fun changeDeleted(state: Boolean) {
        creationState = creationState.copy(deleted = state)
    }

    fun toggleRatingMenu() {
        creationState = creationState.copy(ratingMenuOpen = !creationState.ratingMenuOpen)
    }


    fun saveRating() {
        if(bookState.bookForDetails.userScore == 0){
            bookState.bookForDetails.totalRatings++
        }
        if(creationState.deleted){
            bookState.bookForDetails.totalRatings--
        }
        bookState.bookForDetails.meanScore = (bookState.bookForDetails.meanScore - bookState.bookForDetails.userScore) + creationState.userScore
        bookState.bookForDetails.userScore = creationState.userScore
        creationState = creationState.copy(refresh = !creationState.refresh)
        toggleRatingMenu()
    }

    fun saveReview() {
        viewModelScope.launch {
            val createReview = activityRepository.addActivity(
                creationState.reviewText,
                bookState.bookForDetails.userScore,
                bookState.bookForDetails.bookId,
                UserActivityType.REVIEW
            )
            if (createReview != null && createReview) {
                bookState.bookForDetails.listOfReviews.add(
                    ReviewActivity(
                        user = mainUserState.getMainUser()!!,
                        creationDate = LocalDate.now(),
                        book = bookState.bookForDetails,
                        reviewText = creationState.reviewText,
                        rating = bookState.bookForDetails.userScore
                    )
                )
                bookState.bookForDetails.numberOfReviews++
                if(bookState.bookForDetails.numberOfReviews < 4){
                    bookState.bookForDetails.listOfUserProfilePicturesForReviews.add(0,mainUserState.getMainUser()?.profilePicture ?: "")
                }
                creationState = creationState.copy(reviewCreated = true)
            }
        }
    }
}