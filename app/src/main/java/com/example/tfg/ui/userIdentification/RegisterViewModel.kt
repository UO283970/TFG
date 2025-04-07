package com.example.tfg.ui.userIdentification

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.R
import com.example.tfg.model.AppConstants
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.type.UserRegisterErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterMainState(
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
    var isUserRegistered: Boolean = false,
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val stringResourcesProvider: StringResourcesProvider,
    private val mainRepository: UserRepository,
    private val sharedPreferences: SharedPreferences
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

    fun submit() {
        val correctEmail: Boolean = validateEmailRegister()
        val correctUser: Boolean = validateUsers()
        val correctPassword: Boolean = validatePassword()
        val correctRepeatPassword: Boolean = validateRepeatPassword()

        if (correctEmail && correctUser && correctPassword && correctRepeatPassword) {
            viewModelScope.launch {
                val registeredUser = mainRepository.createUser(formState.email, formState.password, formState.passwordRepeat, formState.userName, formState.userName, "Nada")
                if (registeredUser != null) {
                    if (registeredUser.userRegisterErrors.isNotEmpty()) {
                        formState = formState.copy(isUserRegistered = false)
                        for (error in registeredUser.userRegisterErrors) {
                            handleError(error)
                        }
                    } else {
                        formState = formState.copy(isUserRegistered = true)
                        sharedPreferences.edit() { putString("access_token", registeredUser.tokenId).apply() }
                        sharedPreferences.edit() { putString("refresh_token", registeredUser.refreshToken).apply() }
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
                formState.copy(userNameError = stringResourcesProvider.getString(R.string.error_user_alias_empty))

            UserRegisterErrors.REPEATED_USER_ALIAS -> formState =
                formState.copy(userNameError = stringResourcesProvider.getString(R.string.error_user_alias_repeated))

            UserRegisterErrors.EMPTY_EMAIL -> formState =
                formState.copy(emailError = stringResourcesProvider.getString(R.string.error_email_empty))

            UserRegisterErrors.INVALID_EMAIL -> formState =
                formState.copy(emailError = stringResourcesProvider.getString(R.string.error_email_not_email))

            UserRegisterErrors.ACCOUNT_EXISTS -> formState =
                formState.copy(emailError = stringResourcesProvider.getString(R.string.error_email_used))

            UserRegisterErrors.UNKNOWN__ -> formState =
                formState.copy(emailError = stringResourcesProvider.getString(R.string.error_unkown_error))

        }
    }

    fun userNameChanged(userName: String) {
        formState = formState.copy(userName = userName)
    }

    private fun validateRepeatPassword(): Boolean {
        if (formState.passwordRepeat.isBlank()) {
            handleError(UserRegisterErrors.EMPTY_PASSWORD)
            return false
        }

        if (formState.passwordRepeat != formState.password) {
            handleError(UserRegisterErrors.REPEATED_PASSWORD)
            return false
        }

        formState = formState.copy(passwordRepeatError = null)
        return true
    }

    private fun validatePassword(): Boolean {
        if (formState.password.isBlank()) {
            handleError(UserRegisterErrors.EMPTY_PASSWORD)
            return false
        }

        if (formState.password.length < AppConstants.PASS_MIN_CHARACTERS) {
            handleError(UserRegisterErrors.LONGITUDE_PASSWORD)
            return false
        }

        val hasUpperCase = Regex("[A-Z]").containsMatchIn(formState.password)
        val hasLowerCase = Regex("[a-z]").containsMatchIn(formState.password)
        val hasNumbers = Regex("[1-9]").containsMatchIn(formState.password)
        val hasNonAlphas = Regex("\\w").containsMatchIn(formState.password)

        if (!(hasUpperCase && hasLowerCase && hasNumbers && hasNonAlphas)) {
            handleError(UserRegisterErrors.INVALID_PASSWORD)
            return false
        }

        formState = formState.copy(passwordError = null)
        return true
    }

    private fun validateUsers(): Boolean {
        if (formState.userName.isBlank()) {
            handleError(UserRegisterErrors.EMPTY_USER_ALIAS)
            return false
        }

        /*TODO: Mirar que no exista ya el nombre de usuario*/

        formState = formState.copy(userNameError = null)
        return true
    }

    private fun validateEmailRegister(): Boolean {

        if (formState.email.isBlank()) {
            handleError(UserRegisterErrors.EMPTY_EMAIL)
            return false
        }

        if (!Regex("[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+").matches(formState.email)) {
            handleError(UserRegisterErrors.INVALID_EMAIL)
            return false
        }

        /*TODO: Mirar que no este registrado ya*/

        formState = formState.copy(emailError = null)
        return true
    }

}