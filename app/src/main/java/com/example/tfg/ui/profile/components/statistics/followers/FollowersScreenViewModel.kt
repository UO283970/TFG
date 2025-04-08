package com.example.tfg.ui.profile.components.statistics.followers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.user.User
import com.example.tfg.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FollowersScreenMainState(
    var followersList: ArrayList<User> = arrayListOf<User>(),
    var infoLoaded: Boolean = false
)

@HiltViewModel
class FollowersScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val userRepository: UserRepository
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

    fun deleteFollower(user: User){

    }

}