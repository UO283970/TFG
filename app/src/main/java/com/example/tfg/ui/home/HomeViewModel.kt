package com.example.tfg.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.ListRepository
import com.example.tfg.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeMainState(
    var hasNotifications: Boolean = false,
    var loadingInfo: Boolean = false,
    var animate: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val listRepository: ListRepository,
    val listsState: ListsState,
    val userRepository: UserRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow(
        savedStateHandle.get<HomeMainState>("homeInfo") ?: HomeMainState()
    )

    var homeState: StateFlow<HomeMainState> = _homeState

    init {
        viewModelScope.launch {
            val notificationList = userRepository.getUserNotifications(timeStamp = "");
            if(notificationList != null && notificationList.isNotEmpty()){
                _homeState.value = _homeState.value.copy(hasNotifications = true)
            }else{
                _homeState.value = _homeState.value.copy(hasNotifications = false)
            }
            if( listsState.getDefaultLists().isEmpty()){
                val userLists = listRepository.getBasicListInfo(userId = "")
                if(userLists != null){
                    listsState.setOwnList(ArrayList(userLists))
                }
                val defaultList = listRepository.getUserDefaultLists(userId = "")
                if(defaultList != null){
                    listsState.setDefaultList(ArrayList(defaultList))
                }
            }
            _homeState.value = _homeState.value.copy(loadingInfo = true)
            delay(1000)
            _homeState.value = _homeState.value.copy(animate = true)
        }
    }

    fun goToReading(){
        listsState.setDetailsList(listsState.getDefaultLists()[0])
    }

}