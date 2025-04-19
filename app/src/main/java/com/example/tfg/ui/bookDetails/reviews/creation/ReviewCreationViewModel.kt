package com.example.tfg.ui.bookDetails.reviews.creation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.book.BookState
import com.example.tfg.repository.ActivityRepository
import com.graphQL.type.UserActivityType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReviewCreationState(
    val switchState: Boolean = false,
    val reviewCreated: Boolean = false,
    val reviewText: String = ""
)

@HiltViewModel
class ReviewCreationViewModel @Inject constructor(val activityRepository: ActivityRepository, val bookState: BookState): ViewModel() {

    var creationState by mutableStateOf(ReviewCreationState())

    fun changeSwitch(){
        creationState = creationState.copy(switchState = !creationState.switchState)
    }

    fun changeReviewText(text: String){
        creationState = creationState.copy(reviewText = text)
    }

    fun saveReview(){
        viewModelScope.launch {
            val createReview = activityRepository.addActivity(creationState.reviewText,-1,bookState.bookForDetails.bookId, UserActivityType.REVIEW)
            if(createReview != null && createReview){
                creationState = creationState.copy(reviewCreated = true)
            }
        }
    }
}