package com.example.tfg.ui.profile.components.editScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.R
import com.example.tfg.model.AppConstants
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.type.UserPrivacy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditProfileMainState(
    var userProfilePicture: Int,
    var userName: String,
    var userNameError: String? = null,
    var userAlias: String,
    var userDescription: String,
    var switchState: Boolean,
    var profileEdited: Boolean = false

)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val stringResourcesProvider: StringResourcesProvider,
    private val mainUserState: MainUserState,
    private val userRepository: UserRepository
) : ViewModel() {

    var profileEditState by mutableStateOf(
        EditProfileMainState(
            userProfilePicture = mainUserState.getMainUser()?.profilePicture ?: 0,
            userName = mainUserState.getMainUser()?.userName ?: "",
            userAlias = mainUserState.getMainUser()?.userAlias ?: "",
            userDescription = mainUserState.getMainUser()?.description ?: "",
            switchState = mainUserState.getMainUser()?.privacy == UserPrivacyLevel.PRIVATE
        )
    )

    fun changeUserName(userName: String) {
        profileEditState = profileEditState.copy(userName = userName)
    }

    fun changeUserDescription(userDescription: String) {
        if (profileEditState.userDescription.length <= AppConstants.DESC_MAX_CHARACTERS) {
            profileEditState = profileEditState.copy(
                userDescription = userDescription
            )
        }
    }

    fun changeUserAlias(userAlias: String) {
        profileEditState = profileEditState.copy(userAlias = userAlias)
    }

    fun changeSwitch() {
        profileEditState = profileEditState.copy(switchState = !profileEditState.switchState)
    }

    fun saveButtonOnClick() {
        viewModelScope.launch {
            val userPrivacy = if(profileEditState.switchState) UserPrivacy.PRIVATE else UserPrivacy.PUBLIC
            val newUser = userRepository.updateUser(profileEditState.userAlias,profileEditState.userName,"",profileEditState.userDescription,userPrivacy)
            if(newUser != null && newUser){
                profileEditState =
                    profileEditState.copy(profileEdited = true)

                val userSaved =  mainUserState.getMainUser()
                userSaved?.userAlias = profileEditState.userAlias
                userSaved?.userName = profileEditState.userName
                userSaved?.description = profileEditState.userDescription
                userSaved?.privacy = UserPrivacyLevel.valueOf(userPrivacy.toString())

                mainUserState.setMainUser(userSaved!!)
            }else{
                profileEditState =
                    profileEditState.copy(userNameError = stringResourcesProvider.getString(R.string.error_user_alias_repeated))
            }
        }

    }

    private fun userNameCheck(): Boolean {
        if (profileEditState.userName.isBlank()) {
            profileEditState =
                profileEditState.copy(userNameError = stringResourcesProvider.getString(R.string.error_user_alias_empty))
            return false
        }

        profileEditState = profileEditState.copy(userNameError = null)
        return true
    }

}