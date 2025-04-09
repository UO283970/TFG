package com.example.tfg.ui.userIdentification

import android.content.SharedPreferences
import android.os.Parcelable
import androidx.core.content.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.R
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.ListRepository
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
    var userIsLoggedIn: Boolean = false
) : Parcelable

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val stringResourcesProvider: StringResourcesProvider,
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences,
    private val listRepository: ListRepository,
    private val listsState: ListsState
) : ViewModel() {

    private val _formState = MutableStateFlow(
        savedStateHandle.get<LoginMainState>("loginInfo") ?: LoginMainState()
    )
    var formState: StateFlow<LoginMainState> = _formState

    init {
        viewModelScope.launch {
            checkUserConnected();
        }
    }

    private suspend fun checkUserConnected() {
        if (sharedPreferences.getString("access_token", "")?.isEmpty() == false && sharedPreferences.getString("refresh_token", "")?.isEmpty() == false) {
            var loginUser = userRepository.tokenCheck()
            if (loginUser != null) {
                _formState.value = _formState.value.copy(userIsLoggedIn = true)
            } else {
                var newToken = userRepository.refreshToken(sharedPreferences.getString("refresh_token", "").toString())
                if (newToken != null){
                    sharedPreferences.edit() { putString("access_token", newToken).apply() }
                    _formState.value = _formState.value.copy(userIsLoggedIn = true)
                }
            }
        }
    }

    fun submit() {
        val correctEmail = validateEmail()
        val correctUser = validatePasswordAndUsers()

        if (correctEmail && correctUser) {
            viewModelScope.launch {
                var loginUser = userRepository.login(email = formState.value.email, password = formState.value.password)
                if (loginUser != null) {
                    if (loginUser.userLoginErrors.isNotEmpty()) {
                        _formState.value = _formState.value.copy(userIsLoggedIn = false)
                        for (error in loginUser.userLoginErrors) {
                            handleError(error)
                        }
                    } else {
                        _formState.value = _formState.value.copy(userIsLoggedIn = true)
                        sharedPreferences.edit() { putString("access_token", loginUser.tokenId).apply() }
                        sharedPreferences.edit() { putString("refresh_token", loginUser.refreshToken).apply() }
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
                _formState.value.copy(emailError = stringResourcesProvider.getString(R.string.error_unkown_error))
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