package com.example.tfg.ui.userIdentification

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@Parcelize
data class LoginMainState(
    var email: String = "",
    var emailError: String? = null,
    var password: String = "",
    var passwordError: String? = null,
    var isVisiblePassword: Boolean = false
) : Parcelable

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val stringResourcesProvider: StringResourcesProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _formState = MutableStateFlow(
        savedStateHandle.get<LoginMainState>("loginInfo") ?: LoginMainState()
    )
    var formState: StateFlow<LoginMainState> = _formState

    fun submit() : Boolean{
        val correctEmail = validateEmail()
        val correctUser = validatePasswordAndUsers()
        return correctEmail && correctUser
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
            _formState.value = _formState.value.copy(passwordError = stringResourcesProvider.getString(R.string.error_password_empty))
            return false
        }

        _formState.value = _formState.value.copy(passwordError = null)
        return true
    }

    private fun validateEmail(): Boolean {

        if (_formState.value.email.isBlank()) {
            _formState.value = _formState.value.copy(emailError = stringResourcesProvider.getString(R.string.error_email_empty))
            return false
        }

        if (!Regex("[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+").matches(_formState.value.email)) {
            _formState.value = _formState.value.copy(emailError = stringResourcesProvider.getString(R.string.error_email_not_email))
            return false
        }


        _formState.value = _formState.value.copy(emailError = null)
        return true
    }

}