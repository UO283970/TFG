package com.example.tfg.ui.profile.components.statistics.follows

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.repository.FollowRepository
import com.example.tfg.repository.GlobalErrorHandler
import com.example.tfg.repository.UserRepository
import com.example.tfg.repository.exceptions.AuthenticationException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FollowsScreenMainState(
    var followsList: ArrayList<User> = arrayListOf<User>(),
    var infoLoaded: Boolean = false,
    var refreshInfo: Boolean = false
)

@HiltViewModel
class FollowsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val userRepository: UserRepository,
    val followRepository: FollowRepository,
    val mainUserState: MainUserState
) : ViewModel() {

    val userId = savedStateHandle.get<String>("id")

    var followsInfo by mutableStateOf(FollowsScreenMainState())

    init {
        getUserFollows()
    }

    private fun getUserFollows() {
        viewModelScope.launch {
            val userList = userRepository.getFollowingListUser(userId.toString())
            if (userList != null) {
                followsInfo = followsInfo.copy(followsList = ArrayList(userList))
                followsInfo = followsInfo.copy(infoLoaded = true)
            }
        }
    }

    fun deleteFollow(user: User) {
        viewModelScope.launch {
            val cancel = followRepository.cancelFollow(user.userId)
            try {
                if (cancel != null && cancel) {
                    mainUserState.getMainUser()?.following--
                    followsInfo.followsList.remove(user)
                    user.followState = UserFollowStateEnum.NOT_FOLLOW
                    followsInfo = followsInfo.copy(refreshInfo = !followsInfo.refreshInfo)
                }
            } catch (e: AuthenticationException) {
                GlobalErrorHandler.handle(e)
            }

        }
    }

}