package com.example.tfg.ui.profile.components.statistics.followers

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


data class FollowersScreenMainState(
    var followersList: ArrayList<User> = arrayListOf<User>(),
    var infoLoaded: Boolean = false,
    var refreshInfo: Boolean = false
)

@HiltViewModel
class FollowersScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val userRepository: UserRepository,
    val followRepository: FollowRepository,
    val mainUserState: MainUserState
): ViewModel() {

    val userId = savedStateHandle.get<String>("id")

    var followersInfo by mutableStateOf(FollowersScreenMainState())

    init {
        getUserFollowers()
    }

    private fun getUserFollowers() {
        viewModelScope.launch {
            val userList = userRepository.getFollowersOfUser(userId.toString())
            if(userList != null){
                followersInfo = followersInfo.copy(followersList = ArrayList(userList))
                followersInfo = followersInfo.copy(infoLoaded = true)
            }
        }
    }

    fun changeToNotFollowing(user: User) {
        viewModelScope.launch {
            val cancel = followRepository.cancelFollow(user.userId)
            try {
                if (cancel != null && cancel) {
                    if(UserFollowStateEnum.valueOf(user.followState.toString()) == UserFollowStateEnum.FOLLOWING){
                        mainUserState.getMainUser()?.following--
                    }
                    user.followState = UserFollowStateEnum.NOT_FOLLOW
                    followersInfo = followersInfo.copy(refreshInfo = !followersInfo.refreshInfo)
                }
            } catch (e: AuthenticationException) {
                GlobalErrorHandler.handle(e)
            }

        }
    }

    fun followUser(user: User) {
        viewModelScope.launch {
            val followState = followRepository.followUser(user.userId)
            try {
                if (followState != null) {
                    user.followState = UserFollowStateEnum.valueOf(followState.toString())
                    followersInfo = followersInfo.copy(refreshInfo = !followersInfo.refreshInfo)
                    if( UserFollowStateEnum.valueOf(followState.toString()) == UserFollowStateEnum.FOLLOWING){
                        mainUserState.getMainUser()?.following++
                    }
                }
            } catch (e: AuthenticationException) {
                GlobalErrorHandler.handle(e)
            }
        }
    }

    fun deleteFollower(user: User){
        viewModelScope.launch{
            var deleted = followRepository.deleteFromFollower(user.userId)
            if(deleted != null && deleted){
                followersInfo.followersList.remove(user)
                mainUserState.getMainUser()?.followers--
                followersInfo = followersInfo.copy(refreshInfo = !followersInfo.refreshInfo)
            }
        }
    }

}