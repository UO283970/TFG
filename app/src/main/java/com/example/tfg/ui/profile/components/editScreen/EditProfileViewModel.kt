package com.example.tfg.ui.profile.components.editScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.AppConstants
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class EditProfileMainState(
    var userProfilePicture: Int,
    var userName: String,
    var userNameError: String? = null,
    var userAlias: String,
    var userDescription: String,
    var switchState: Boolean

)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val stringResourcesProvider: StringResourcesProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val user = User(
        /*TODO: Le pasas el usuario que esta conectado porque se accede desde el menú*/
        "Nombre de Usuario2",
        R.drawable.prueba,
        UserPrivacyLevel.PUBLIC,
        UserFollowStateEnum.OWN,
        userName = "Nombre de Usuario2"
    )

    var profileEditState by mutableStateOf(
        EditProfileMainState(
            userProfilePicture = user?.profilePicture ?: 0,
            userName = user?.userName ?: "",
            userAlias = user?.userAlias ?: "",
            userDescription = user?.description ?: "",
            switchState = user?.privacy == UserPrivacyLevel.PRIVATE
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

    fun saveButtonOnClick(): Boolean {
        /*TODO: Guardar los cambios del usuario en la base de datos*/
        return userNameCheck()
    }

    private fun userNameCheck(): Boolean {
        if (profileEditState.userName.isBlank()) {
            profileEditState =
                profileEditState.copy(userNameError = stringResourcesProvider.getString(R.string.error_user_alias_empty))
            return false
        }

        /*TODO: Mirar que no exista ya el nombre de usuario*/

        profileEditState = profileEditState.copy(userNameError = null)
        return true
    }

}