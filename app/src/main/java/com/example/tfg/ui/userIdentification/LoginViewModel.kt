package com.example.tfg.ui.userIdentification

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.R
import com.example.tfg.model.security.TokenRepository
import com.example.tfg.model.user.PassEncryption
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.type.UserLoginErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class LoginMainState(
    var email: String = "",
    var emailError: String? = null,
    var password: String = "",
    var passwordError: String? = null,
    var isVisiblePassword: Boolean = false,
    var userIsLoggedIn: Boolean = false,
    var chargingInfo: Boolean = true,
) : Parcelable

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val stringResourcesProvider: StringResourcesProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {

    private val _formState = MutableStateFlow(
        savedStateHandle.get<LoginMainState>("loginInfo") ?: LoginMainState()
    )
    var formState: StateFlow<LoginMainState> = _formState

    init {
        viewModelScope.launch {
            checkUserConnected()
        }
    }

    private suspend fun checkUserConnected() {
        if (tokenRepository.getAccessToken()?.isEmpty() == false && tokenRepository.getRefreshToken()?.isEmpty() == false) {
            var loginUser = userRepository.tokenCheck()
            if (loginUser != null) {
                _formState.value = _formState.value.copy(userIsLoggedIn = true)
            } else {
                var newToken = userRepository.refreshToken(tokenRepository.getRefreshToken().toString())
                if (newToken != null){
                    tokenRepository.saveTokens(newToken,tokenRepository.getRefreshToken().toString())
                    _formState.value = _formState.value.copy(userIsLoggedIn = true)
                }
            }
        }
        _formState.value = _formState.value.copy(chargingInfo = false)
    }

    fun submit() {
        val correctEmail = validateEmail()
        val correctUser = validatePasswordAndUsers()

        val encryptedPass = PassEncryption.encrypt(formState.value.password)

        if (correctEmail && correctUser) {
            viewModelScope.launch {
                var loginUser = userRepository.login(email = formState.value.email, password = encryptedPass)
                if (loginUser != null) {
                    if (loginUser.userLoginErrors.isNotEmpty()) {
                        _formState.value = _formState.value.copy(userIsLoggedIn = false)
                        for (error in loginUser.userLoginErrors) {
                            handleError(error)
                        }
                    } else {
                        _formState.value = _formState.value.copy(userIsLoggedIn = true)
                        tokenRepository.saveTokens(loginUser.tokenId,loginUser.refreshToken)
                    }
                } else {
                    _formState.value = _formState.value.copy(userIsLoggedIn = false)
                    handleError(UserLoginErrors.USER_NOT_FOUND)
                }
            }
        }
    }

    private fun handleError(error: UserLoginErrors) {
        when (error) {
            UserLoginErrors.USER_NOT_FOUND -> {
                _formState.value = _formState.value.copy(passwordError = stringResourcesProvider.getString(R.string.error_user_login))
                _formState.value = _formState.value.copy(emailError = stringResourcesProvider.getString(R.string.error_user_login))
            }

            UserLoginErrors.EMPTY_EMAIL -> _formState.value =
                _formState.value.copy(emailError = stringResourcesProvider.getString(R.string.error_email_empty))

            UserLoginErrors.INVALID_EMAIL -> _formState.value =
                _formState.value.copy(emailError = stringResourcesProvider.getString(R.string.error_email_not_email))

            UserLoginErrors.EMPTY_PASSWORD -> _formState.value =
                _formState.value.copy(passwordError = stringResourcesProvider.getString(R.string.error_password_empty))

            UserLoginErrors.UNKNOWN__ -> _formState.value =
                _formState.value.copy(emailError = stringResourcesProvider.getString(R.string.error_unknown_error))
        }
    }

    fun visiblePassword(isVisiblePassword: Boolean) {
        _formState.value = _formState.value.copy(isVisiblePassword = isVisiblePassword)
    }

    fun passwordChanged(password: String) {
        _formState.value = _formState.value.copy(password = password)
    }

    fun emailChanged(email: String) {
        _formState.value = _formState.value.copy(email = email)
    }

    private fun validatePasswordAndUsers(): Boolean {
        if (_formState.value.password.isBlank()) {
            handleError(UserLoginErrors.EMPTY_PASSWORD)
            return false
        }

        _formState.value = _formState.value.copy(passwordError = null)
        return true
    }

    private fun validateEmail(): Boolean {

        if (_formState.value.email.isBlank()) {
            handleError(UserLoginErrors.EMPTY_EMAIL)
            return false
        }

        if (!Regex("[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+").matches(_formState.value.email)) {
            handleError(UserLoginErrors.INVALID_EMAIL)
            return false
        }


        _formState.value = _formState.value.copy(emailError = null)
        return true
    }

}