package com.example.tfg.ui.bookDetails.reviews

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.book.Book
import com.example.tfg.model.book.BookState
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.repository.ActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class BookReviewsState(
    val listOfReviews: ArrayList<ReviewActivity> = arrayListOf<ReviewActivity>(),
    val loadInfo: Boolean = false
)

@HiltViewModel
class ReviewsScreenViewModel @Inject constructor(
    val activityRepository: ActivityRepository,
    val bookState: BookState,
    val mainUserState: MainUserState
): ViewModel() {

    var bookReviewState by mutableStateOf(BookReviewsState())

    init {
        getReviews()
    }

    private fun getReviews() {
        val review = ReviewActivity(
            user = User("Nuevo Usuario"),
            creationDate = LocalDate.now(),
            book = Book("Palabras Radiantes", "Brandon Sanderson"),
            reviewText = "Esto es una review de prueba buenos dias espero que el texto se lo suficientmente largo wowowowowowowowo oow ow o w o o Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse tristique condimentum justo ac sodales. Vivamus a volutpat tellus. Proin sagittis sit amet leo id maximus.",
            rating = -1
        )


        viewModelScope.launch {
            bookState.bookForDetails.listOfReviews = arrayListOf<ReviewActivity>()
            val reviews = activityRepository.getAllReviewsForBook(bookState.bookForDetails.bookId)
            if(reviews != null){
                bookState.bookForDetails.listOfReviews.addAll(reviews)
                bookReviewState = bookReviewState.copy(loadInfo = true)
            }
        }

    }

}