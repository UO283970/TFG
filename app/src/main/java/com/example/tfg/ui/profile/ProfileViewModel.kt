package com.example.tfg.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.repository.GlobalErrorHandler
import com.example.tfg.repository.UserRepository
import com.example.tfg.repository.exceptions.AuthenticationException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileMainState(
    var profileReviews: ArrayList<Activity> = arrayListOf(),
    var infoLoaded: Boolean = false,
    var mainUserState: MainUserState,
    val isRefreshing: Boolean = false,
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    val mainUserState: MainUserState,
    val listsState: ListsState
) : ViewModel() {

    private val _profileInfo = MutableStateFlow(
        ProfileMainState(mainUserState = mainUserState)
    )

    val profileInfo: StateFlow<ProfileMainState> = _profileInfo

    init {
        viewModelScope.launch {
            if (_profileInfo.value.mainUserState.getMainUser() == null) {
                try {
                    val connectedUser = userRepository.getAuthenticatedUserInfo()
                    if (connectedUser != null) {
                        val userObtained = connectedUser
                        _profileInfo.value = _profileInfo.value.copy(infoLoaded = true)
                        _profileInfo.value.mainUserState.setMainUser(userObtained)
                        listsState.setDefaultList(ArrayList(mainUserState.getMainUser()!!.defaultList))
                        listsState.setOwnList(ArrayList(mainUserState.getMainUser()!!.userList))
                    }
                } catch (e: AuthenticationException) {
                    GlobalErrorHandler.handle(e)
                }
            }else{
                _profileInfo.value = _profileInfo.value.copy(infoLoaded = true)
            }
        }
    }

    fun editProfile(){
        _profileInfo.value = _profileInfo.value.copy(infoLoaded = true)
    }

    fun listDetails(bookList: BookList) {
        listsState.setDetailsList(bookList)
    }

    fun refreshProfile() {
        _profileInfo.value = _profileInfo.value.copy(isRefreshing = true)

        viewModelScope.launch {
            try {
                val connectedUser = userRepository.getAuthenticatedUserInfo()
                if (connectedUser != null) {
                    val userObtained = connectedUser
                    _profileInfo.value = _profileInfo.value.copy(isRefreshing = false)
                    _profileInfo.value.mainUserState.setMainUser(userObtained)
                }
            } catch (e: AuthenticationException) {
                GlobalErrorHandler.handle(e)
            }
        }
    }

}
