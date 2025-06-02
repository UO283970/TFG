package com.example.tfg.ui.profile.components.editScreen

import android.content.ContentResolver
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
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
import okio.FileNotFoundException
import java.util.Locale
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

data class EditProfileMainState(
    var userProfilePicture: String,
    var userName: String,
    var userNameError: String? = null,
    var userAlias: String,
    var userDescription: String,
    var userImageUri: Uri,
    var switchState: Boolean,
    var profileEdited: Boolean = false,
    var showDialog: Boolean = false,
    var checkGalleryPermission: Boolean = false

)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val stringResourcesProvider: StringResourcesProvider,
    val mainUserState: MainUserState,
    private val userRepository: UserRepository,
    private val contentResolver: ContentResolver
) : ViewModel() {

    var profileEditState by mutableStateOf(
        EditProfileMainState(
            userProfilePicture = mainUserState.getMainUser()?.profilePicture ?: "",
            userName = mainUserState.getMainUser()?.userName ?: "",
            userAlias = mainUserState.getMainUser()?.userAlias ?: "",
            userDescription = mainUserState.getMainUser()?.description ?: "",
            switchState = mainUserState.getMainUser()?.privacy == UserPrivacyLevel.PRIVATE,
            userImageUri = mainUserState.getMainUser()?.profilePicture?.toUri() ?: "".toUri()
        )
    )

    fun changeUserName(userName: String) {
        if (profileEditState.userName.length < AppConstants.USER_NAME_MAX_CHARACTERS) {
            profileEditState = profileEditState.copy(userName = userName)
        }
    }

    fun changeUserDescription(userDescription: String) {
        if (profileEditState.userDescription.length < AppConstants.DESC_MAX_CHARACTERS) {
            profileEditState = profileEditState.copy(
                userDescription = userDescription
            )
        }
    }

    fun changeSwitch() {
        profileEditState = profileEditState.copy(switchState = !profileEditState.switchState)
    }

    fun changePermission() {
        profileEditState = profileEditState.copy(checkGalleryPermission = true)
    }

    fun changeDialog() {
        profileEditState = profileEditState.copy(showDialog = !profileEditState.showDialog)
    }

    fun changeUserAlias(userAlias: String) {
        if (profileEditState.userAlias.length < AppConstants.USER_ALIAS_MAX_CHARACTERS) {
            profileEditState = profileEditState.copy(userAlias = userAlias)
        }
    }

    fun setImageUri(imageUri: Uri) {
        profileEditState = profileEditState.copy(userImageUri = imageUri)
    }


    @OptIn(ExperimentalEncodingApi::class)
    fun saveButtonOnClick() {
        if(userNameCheck()) {
            viewModelScope.launch {
                val userPrivacy = if (profileEditState.switchState) UserPrivacy.PRIVATE else UserPrivacy.PUBLIC
                var userImageBase64 = try {
                    contentResolver.openInputStream(profileEditState.userImageUri).use { inputStream ->
                        val bytes = inputStream?.readBytes()
                        Base64.encode(bytes!!)
                    }
                } catch (_: FileNotFoundException) {
                    ""
                }

                val newUser = userRepository.updateUser(
                    profileEditState.userAlias.lowercase(Locale.getDefault()),
                    profileEditState.userName,
                    userImageBase64,
                    profileEditState.userDescription,
                    userPrivacy
                )
                if (newUser != null && !newUser.isBlank()) {
                    profileEditState =
                        profileEditState.copy(profileEdited = true)

                    val userSaved = mainUserState.getMainUser()
                    userSaved?.userAlias = profileEditState.userAlias
                    userSaved?.userName = profileEditState.userName
                    userSaved?.description = profileEditState.userDescription
                    userSaved?.privacy = UserPrivacyLevel.valueOf(userPrivacy.toString())
                    userSaved?.profilePicture = newUser

                    mainUserState.setMainUser(userSaved!!)
                } else {
                    profileEditState =
                        profileEditState.copy(userNameError = stringResourcesProvider.getString(R.string.error_user_alias_repeated))
                }
            }
        }
    }

    private fun userNameCheck(): Boolean {
        if (profileEditState.userAlias.isBlank()) {
            profileEditState =
                profileEditState.copy(userNameError = stringResourcesProvider.getString(R.string.error_user_alias_empty))
            return false
        }

        if (profileEditState.userAlias.length < 3 || profileEditState.userAlias.length > 20 ) {
            profileEditState =
                profileEditState.copy(userNameError = stringResourcesProvider.getString(R.string.error_user_alias_length))
            return false
        }

        val hasInvalidSpecialChars = Regex("[^a-zA-Z0-9_.]").containsMatchIn(profileEditState.userAlias)

        if (hasInvalidSpecialChars) {
            profileEditState =
                profileEditState.copy(userNameError = stringResourcesProvider.getString(R.string.error_user_alias_especial_chars))
            return false
        }

        val startsOrEndsWithDotOrUnderscore = Regex("^[._]|[._]$").containsMatchIn(profileEditState.userAlias)

        if (startsOrEndsWithDotOrUnderscore) {
            profileEditState =
                profileEditState.copy(userNameError = stringResourcesProvider.getString(R.string.error_user_alias_especial_chars_star_end))
            return false
        }

        profileEditState = profileEditState.copy(userNameError = null)
        return true
    }

}