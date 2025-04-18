package com.example.tfg.ui.profile.othersProfile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.repository.FollowRepository
import com.example.tfg.repository.GlobalErrorHandler
import com.example.tfg.repository.UserRepository
import com.example.tfg.repository.exceptions.AuthenticationException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileMainState(
    val user: User = User(""),
    var profileReviews: ArrayList<Activity> = arrayListOf(),
    val userInfoLoaded: Boolean = false,
    val refreshUserState: Boolean = false,
    val isRefreshing: Boolean = false
)

@HiltViewModel
class OthersProfileViewModel @Inject constructor(
    private val listsState: ListsState,
    private val userRepository: UserRepository,
    val followRepository: FollowRepository,
    val mainUserState: MainUserState,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val userId = savedStateHandle.get<String?>("userId")

    private val _profileInfo = MutableStateFlow(
        ProfileMainState()
    )

    val profileInfo: StateFlow<ProfileMainState> = _profileInfo

    init {
        getAllUserInfo()
    }

    fun getAllUserInfo() {
        viewModelScope.launch {
            var expandedUser = userRepository.getAllUserInfo(userId.toString())
            if (expandedUser != null) {
                _profileInfo.value = _profileInfo.value.copy(user = expandedUser)
                _profileInfo.value = _profileInfo.value.copy(userInfoLoaded = true)
                _profileInfo.value = _profileInfo.value.copy(isRefreshing = true)
            }
        }
    }

    fun listDetails(bookList: BookList) {
        listsState.setDetailsList(bookList)
    }

    fun changeToNotFollowing() {
        viewModelScope.launch {
            val cancel = followRepository.cancelFollow(_profileInfo.value.user.userId)
            try {
                if (cancel != null && cancel) {
                    if(_profileInfo.value.user.followState == UserFollowStateEnum.FOLLOWING){
                        mainUserState.getMainUser()?.following--
                    }
                    getAllUserInfo()
                }
            } catch (e: AuthenticationException) {
                GlobalErrorHandler.handle(e)
            }

        }
    }

    fun followUser() {
        viewModelScope.launch {
            val followState = followRepository.followUser(_profileInfo.value.user.userId)
            try {
                if (followState != null) {
                    _profileInfo.value.user.followState = UserFollowStateEnum.valueOf(followState.toString())
                    if (_profileInfo.value.user.followState == UserFollowStateEnum.FOLLOWING) {
                        _profileInfo.value.user.followers++
                        mainUserState.getMainUser()?.following++
                        getAllUserInfo()
                    }
                    _profileInfo.value = _profileInfo.value.copy(refreshUserState = !_profileInfo.value.refreshUserState)
                }
            } catch (e: AuthenticationException) {
                GlobalErrorHandler.handle(e)
            }
        }
    }

    fun refreshUser(){
        _profileInfo.value = _profileInfo.value.copy(isRefreshing = true)
        getAllUserInfo()
    }


}
