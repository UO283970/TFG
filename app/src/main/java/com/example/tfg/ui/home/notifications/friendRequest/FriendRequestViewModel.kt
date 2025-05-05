package com.example.tfg.ui.home.notifications.friendRequest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.User
import com.example.tfg.repository.FollowRepository
import com.example.tfg.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RequestMainState(
    val friendRequests: ArrayList<User> = arrayListOf(),
    val loadingRequests: Boolean = true,
    val repaintList: Boolean = false,
)

@HiltViewModel
class FriendRequestViewModel @Inject constructor(
    val userRepository: UserRepository,
    val followRepository: FollowRepository,
    val mainUserState: MainUserState
) :
    ViewModel() {
    var requestMainState by mutableStateOf(RequestMainState())

    init {
        getFriendRequests()
    }

    fun acceptFriendRequest(user: User) {
        viewModelScope.launch {
            val added = followRepository.acceptRequest(user.userId)
            if (added != null && added) {
                requestMainState.friendRequests.remove(user)
                mainUserState.getMainUser()?.followers++
                requestMainState = requestMainState.copy(repaintList = !requestMainState.repaintList)
            }
        }
    }

    fun deleteFriendRequest(user: User) {
        viewModelScope.launch {
            val removed = followRepository.cancelRequest(user.userId)
            if (removed != null && removed) {
                requestMainState.friendRequests.remove(user)
                requestMainState = requestMainState.copy(repaintList = !requestMainState.repaintList)
            }
        }
    }

    private fun getFriendRequests() {
        requestMainState =
            requestMainState.copy(loadingRequests = true)
        viewModelScope.launch {
            val userRequests = userRepository.getUserFollowRequest()
            if (userRequests != null) {
                requestMainState =
                    requestMainState.copy(friendRequests = ArrayList(userRequests))
                requestMainState =
                    requestMainState.copy(loadingRequests = false)
            }
        }
    }
}