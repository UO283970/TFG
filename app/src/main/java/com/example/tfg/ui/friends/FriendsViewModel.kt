package com.example.tfg.ui.friends

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.repository.ActivityRepository
import com.example.tfg.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class FriendsMainState(
    var userQuery: String = "",
    var queryResult: ArrayList<User> = arrayListOf(),
    var expandedSearchBar: Boolean = false,
    var followedActivity: ArrayList<Activity> = arrayListOf<Activity>(),
    var activityLoaded: Boolean = false,
    var userExpandedInfo: User? = null,
    var userExpandedInfoLoaded: Boolean = false,

) : Parcelable

@HiltViewModel
class FriendsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val activityRepository: ActivityRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _friendsInfo = MutableStateFlow(
        savedStateHandle.get<FriendsMainState>("friendsInfo") ?: FriendsMainState()
    )
    val friendsInfo: StateFlow<FriendsMainState> = _friendsInfo

    init {
        viewModelScope.launch {
            getFollowedActivity()
        }
    }


    fun changeExpandedSearchBar(change: Boolean) {
        _friendsInfo.update {
            val newState = it.copy(expandedSearchBar = change,queryResult = arrayListOf(), userQuery = "")
            newState
        }
    }

    fun onlyChangeExpandedSearchBar(change: Boolean) {
        _friendsInfo.update {
            val newState = it.copy(expandedSearchBar = change)
            newState
        }
    }

    fun userFriendQueryChange(userQuery: String) {
        _friendsInfo.value = _friendsInfo.value.copy(userQuery = userQuery)
    }

    fun saveState() {
        _friendsInfo.value = _friendsInfo.value.copy(userExpandedInfoLoaded = true)
    }

    fun changeExpandedInfoLoaded(){
        _friendsInfo.value = _friendsInfo.value.copy(userExpandedInfoLoaded = false)
    }

    fun searchUsers() {
        viewModelScope.launch {
            val result = userRepository.getUserSearchInfo(_friendsInfo.value.userQuery)
            if(result != null){
                _friendsInfo.value = _friendsInfo.value.copy(queryResult = ArrayList(result))
            }
        }
    }

    private fun getFollowedActivity(){
        viewModelScope.launch {
            val activity = activityRepository.getAllFollowedActivity()
            if(activity != null){
                _friendsInfo.value = _friendsInfo.value.copy(followedActivity = ArrayList(activity))
                _friendsInfo.value = _friendsInfo.value.copy(activityLoaded = true)
            }
        }
    }
}