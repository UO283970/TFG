package com.example.tfg.ui.profile.components.configuration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.model.security.TokenRepository
import com.example.tfg.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ConfigurationState(
    var toLogin: Boolean = false,
    var deleteDialog: Boolean = false
)

@HiltViewModel
class ConfigurationScreenViewModel @Inject constructor(val userRepository: UserRepository, val tokenRepository: TokenRepository): ViewModel() {

    var confState by mutableStateOf(ConfigurationState())

    fun logout(){
        viewModelScope.launch {
            val response = userRepository.logout()
            if(response != null && response != ""){
                tokenRepository.clearTokens()
                confState = confState.copy(toLogin = true)
            }
        }
    }

    fun toggleDeleteDialog(){
        confState = confState.copy(deleteDialog = !confState.deleteDialog)
    }

    fun deleteAccount(){
        viewModelScope.launch {
            val response = userRepository.deleteUser()
            if(response != null && response){
                tokenRepository.clearTokens()
                confState = confState.copy(toLogin = true)
            }
        }
    }

}