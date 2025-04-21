package com.example.tfg.ui.userIdentification

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
import com.example.tfg.model.security.TokenRepository
import com.example.tfg.model.user.UserRegistrationState
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.type.UserRegisterErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.FileNotFoundException
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

data class RegisterMainState(
    var userAlias: String = "",
    var userAliasError: String? = null,
    var userName: String = "",
    var userNameError: String? = null,
    var email: String = "",
    var emailError: String? = null,
    var password: String = "",
    var passwordError: String? = null,
    var isVisiblePassword: Boolean = false,
    var passwordRepeat: String = "",
    var passwordRepeatError: String? = null,
    var isVisiblePasswordRepeat: Boolean = false,
    var checkPreConditions: Boolean = false,
    var isUserRegistered: Boolean = false,
    var showDialog: Boolean = false,
    var checkGalleryPermission: Boolean = false,
    var userImageUri: Uri = "".toUri()
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val stringResourcesProvider: StringResourcesProvider,
    private val mainRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val contentResolver: ContentResolver,
    val userRegistrationState: UserRegistrationState
) : ViewModel() {

    var formState by mutableStateOf(RegisterMainState())

    fun emailChanged(email: String) {
        formState = formState.copy(email = email)
    }

    fun passwordChanged(password: String) {
        formState = formState.copy(password = password)
    }

    fun visiblePassword(isVisiblePassword: Boolean) {
        formState = formState.copy(isVisiblePassword = isVisiblePassword)
    }

    fun passwordRepeatChanged(repeatPassword: String) {
        formState = formState.copy(passwordRepeat = repeatPassword)
    }

    fun visiblePasswordRepeat(isVisibleRepeatPassword: Boolean) {
        formState = formState.copy(isVisiblePasswordRepeat = isVisibleRepeatPassword)
    }

    fun changePermission() {
        formState = formState.copy(checkGalleryPermission = true)
    }

    fun changePreconditions() {
        formState = formState.copy(checkPreConditions = false)
    }

    fun changeDialog() {
        formState = formState.copy(showDialog = !formState.showDialog)
    }

    fun setImageUri(imageUri: Uri) {
        formState = formState.copy(userImageUri = imageUri)
    }

    fun checkEmailAndPass(){
        userRegistrationState.email = formState.email
        userRegistrationState.password = formState.password
        userRegistrationState.passwordRepeat = formState.passwordRepeat

        val correctEmail: Boolean = validateEmailRegister()
        val correctPassword: Boolean = validatePassword()
        val correctRepeatPassword: Boolean = validateRepeatPassword()

        if (correctEmail && correctPassword && correctRepeatPassword) {
            viewModelScope.launch {

                val registeredUser = mainRepository.checkUserEmailAndPass(formState.email, formState.password, formState.passwordRepeat)
                if (registeredUser != null) {
                    if (registeredUser.userRegisterErrors.isNotEmpty()) {
                        formState = formState.copy(checkPreConditions = false)
                        for (error in registeredUser.userRegisterErrors) {
                            handleError(error)
                        }
                    } else {
                        formState = formState.copy(checkPreConditions = true)
                    }
                }else{
                    formState = formState.copy(checkPreConditions = false)
                    handleError(UserRegisterErrors.UNKNOWN__)
                }
            }
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun submit() {
        val correctEmail: Boolean = validateEmailRegister()
        val correctUser: Boolean = validateUsers()
        val correctPassword: Boolean = validatePassword()
        val correctRepeatPassword: Boolean = validateRepeatPassword()

        if (correctEmail && correctUser && correctPassword && correctRepeatPassword) {
            viewModelScope.launch {
                var userImageBase64 = try {
                    contentResolver.openInputStream(formState.userImageUri).use { inputStream ->
                        val bytes = inputStream?.readBytes()
                        Base64.encode(bytes!!)
                    }
                } catch (_: FileNotFoundException) {
                    ""
                }

                val registeredUser = mainRepository.createUser( userRegistrationState.email , userRegistrationState.password, userRegistrationState.passwordRepeat, formState.userAlias, formState.userName, userImageBase64)
                if (registeredUser != null) {
                    if (registeredUser.userRegisterErrors.isNotEmpty()) {
                        formState = formState.copy(isUserRegistered = false)
                        for (error in registeredUser.userRegisterErrors) {
                            handleError(error)
                        }
                    } else {
                        formState = formState.copy(isUserRegistered = true)
                        tokenRepository.saveTokens(registeredUser.tokenId,registeredUser.refreshToken)
                    }
                }else{
                    formState = formState.copy(isUserRegistered = false)
                    handleError(UserRegisterErrors.UNKNOWN__)
                }
            }
        }
    }

    private fun handleError(error: UserRegisterErrors) {
        when (error) {
            UserRegisterErrors.REPEATED_PASSWORD -> formState =
                formState.copy(passwordRepeatError = stringResourcesProvider.getString(R.string.error_passwordRepeat))

            UserRegisterErrors.EMPTY_PASSWORD -> formState =
                formState.copy(passwordError = stringResourcesProvider.getString(R.string.error_password_empty))

            UserRegisterErrors.LONGITUDE_PASSWORD -> formState = formState.copy(
                passwordError = stringResourcesProvider.getStringWithParameters(R.string.error_password_length, AppConstants.PASS_MIN_CHARACTERS)
            )

            UserRegisterErrors.INVALID_PASSWORD -> formState =
                formState.copy(passwordError = stringResourcesProvider.getString(R.string.error_password_rules))

            UserRegisterErrors.EMPTY_USER_ALIAS -> formState =
                formState.copy(userAliasError = stringResourcesProvider.getString(R.string.error_user_alias_empty))

            UserRegisterErrors.REPEATED_USER_ALIAS -> formState =
                formState.copy(userAliasError = stringResourcesProvider.getString(R.string.error_user_alias_repeated))

            UserRegisterErrors.EMPTY_EMAIL -> formState =
                formState.copy(emailError = stringResourcesProvider.getString(R.string.error_email_empty))

            UserRegisterErrors.INVALID_EMAIL -> formState =
                formState.copy(emailError = stringResourcesProvider.getString(R.string.error_email_not_email))

            UserRegisterErrors.ACCOUNT_EXISTS -> formState =
                formState.copy(emailError = stringResourcesProvider.getString(R.string.error_email_used))

            UserRegisterErrors.UNKNOWN__ -> formState =
                formState.copy(emailError = stringResourcesProvider.getString(R.string.error_unknown_error))

        }
    }

    fun userAliasChanged(userAlias: String) {
        formState = formState.copy(userAlias = userAlias)
    }

    fun userNameChanged(userName: String) {
        formState = formState.copy(userName = userName)
    }

    private fun validateRepeatPassword(): Boolean {
        if (userRegistrationState.passwordRepeat != userRegistrationState.password) {
            handleError(UserRegisterErrors.REPEATED_PASSWORD)
            return false
        }

        formState = formState.copy(passwordRepeatError = null)
        return true
    }

    private fun validatePassword(): Boolean {
        if (userRegistrationState.password.isBlank()) {
            handleError(UserRegisterErrors.EMPTY_PASSWORD)
            return false
        }

        if (userRegistrationState.password.length < AppConstants.PASS_MIN_CHARACTERS) {
            handleError(UserRegisterErrors.LONGITUDE_PASSWORD)
            return false
        }

        val hasUpperCase = Regex("[A-Z]").containsMatchIn(userRegistrationState.password)
        val hasLowerCase = Regex("[a-z]").containsMatchIn(userRegistrationState.password)
        val hasNumbers = Regex("[1-9]").containsMatchIn(userRegistrationState.password)
        val hasNonAlphas = Regex("[^\\w\\s:]").containsMatchIn(userRegistrationState.password)
        val noWhiteSpaces = Regex("\\s").containsMatchIn(userRegistrationState.password)

        if (!(hasUpperCase && hasLowerCase && hasNumbers && hasNonAlphas && !noWhiteSpaces)) {
            handleError(UserRegisterErrors.INVALID_PASSWORD)
            return false
        }

        formState = formState.copy(passwordError = null)
        return true
    }

    private fun validateUsers(): Boolean {
        if (formState.userAlias.isBlank()) {
            handleError(UserRegisterErrors.EMPTY_USER_ALIAS)
            return false
        }

        formState = formState.copy(userAliasError = null)
        return true
    }

    private fun validateEmailRegister(): Boolean {

        if (userRegistrationState.email.isBlank()) {
            handleError(UserRegisterErrors.EMPTY_EMAIL)
            return false
        }

        if (!Regex("[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+").matches(userRegistrationState.email)) {
            handleError(UserRegisterErrors.INVALID_EMAIL)
            return false
        }

        formState = formState.copy(emailError = null)
        return true
    }

}