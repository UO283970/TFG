package com.example.tfg.ui.profile.components.statistics.reviews

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReviewsScreenMainState(
    var profileReviews: ArrayList<Activity> = arrayListOf<Activity>(),
    var infoLoaded: Boolean = false
)

@HiltViewModel
class ReviewsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val userRepository: UserRepository
): ViewModel(){

    val userId = savedStateHandle.get<String>("id")

    var profileReviewsInfo by mutableStateOf(ReviewsScreenMainState())

    init {
        getUserReviews()
    }

    fun getUserReviews() {
        viewModelScope.launch {
            val reviewList = userRepository.getUsersReviews(userId.toString())
            if(reviewList != null){
                profileReviewsInfo = profileReviewsInfo.copy(profileReviews = ArrayList(reviewList))
                profileReviewsInfo = profileReviewsInfo.copy(infoLoaded = true)
            }
        }
    }
}

