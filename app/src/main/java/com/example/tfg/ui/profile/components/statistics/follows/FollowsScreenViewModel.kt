package com.example.tfg.ui.profile.components.statistics.follows

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


data class FollowsScreenMainState(
    var followsList: ArrayList<User> = arrayListOf<User>(),
    var infoLoaded: Boolean = false
)

@HiltViewModel
class FollowsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val userRepository: UserRepository): ViewModel() {

    val userId = savedStateHandle.get<String>("id")

    var followsInfo by mutableStateOf(FollowsScreenMainState())

    init {
        getUserFollows()
    }

    private fun getUserFollows() {
        viewModelScope.launch {
            val userList = userRepository.getFollowingListUser(userId.toString())
            if(userList != null){
                followsInfo = followsInfo.copy(followsList = ArrayList(userList))
                followsInfo = followsInfo.copy(infoLoaded = true)
            }
        }
    }

    fun deleteFollow(user: User){

    }

}