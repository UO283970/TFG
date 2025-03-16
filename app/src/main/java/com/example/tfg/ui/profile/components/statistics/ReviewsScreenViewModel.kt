package com.example.tfg.ui.profile.components.statistics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

data class ReviewsScreenMainState(
    var profileReviews: ArrayList<Activity>
)

@HiltViewModel
class ReviewsScreenViewModel @Inject constructor(): ViewModel(){

    var profileReviewsInfo by mutableStateOf(ReviewsScreenMainState(getUserReviews()))

}

fun getUserReviews(): ArrayList<Activity> {
    val followedActivity: ArrayList<Activity> = arrayListOf()
    //TODO: Obtener las reviews del usuario seleccionado
    val libroTest = Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba)
    val userForTesting =
        User("Nombre de Usuario", R.drawable.prueba, UserPrivacyLevel.PUBLIC, UserFollowStateEnum.REQUESTED)
    val reviewForTest = ReviewActivity(
        userForTesting,
        LocalDate.now(),
        libroTest,
        "Muy guapo el libro"
    )

    followedActivity.add(reviewForTest)
   return followedActivity
}