package com.example.tfg.ui.profile.othersProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import com.example.tfg.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class ProfileMainState(
    val user: User,
    var profileReviews: ArrayList<Activity> = arrayListOf(),
    val userInfoLoaded: Boolean = false
)
@HiltViewModel
class OthersProfileViewModel @Inject constructor(
    private val listsState: ListsState,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val gson: Gson = GsonBuilder().create()
    val userJson = savedStateHandle.get<String?>("user")
    private val user = gson.fromJson(userJson, User::class.java)

    var profileInfo by mutableStateOf(
        ProfileMainState(user = user)
    )

    init {
        getAllUserInfo()
    }

    fun getAllUserInfo(){
        viewModelScope.launch {
            var expandedUser = userRepository.getAllUserInfo(profileInfo.user.userId)
            if (expandedUser != null) {
                profileInfo = profileInfo.copy(user = expandedUser)
                profileInfo = profileInfo.copy(userInfoLoaded = true)
            }
        }
    }

    fun getUserReviews() {
        val followedActivity: ArrayList<Activity> = arrayListOf()
        //TODO: Obtener la actividad de las reviews del usuario seleccionado
        val libroTest = Book("Words Of Radiance", "Brandon Sanderson")
        val userForTesting =
            User("Nombre de Usuario", R.drawable.prueba, UserPrivacyLevel.PUBLIC, UserFollowStateEnum.REQUESTED)
        val reviewForTest = ReviewActivity(
            userForTesting,
            LocalDate.now(),
            libroTest,
            "Muy guapo el libro"
        )

        followedActivity.add(reviewForTest)
        profileInfo = profileInfo.copy(profileReviews = followedActivity)
    }
    fun listDetails(bookList: BookList) {
        listsState.setDetailsList(bookList)
    }


}
