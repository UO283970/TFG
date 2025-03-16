package com.example.tfg.ui.userIdentification

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {

    var formState by mutableStateOf(LoginMainState())

    fun submit() : Boolean{
        val correctEmail = validateEmail()
        val correctUser = validatePasswordAndUsers()
        return correctEmail && correctUser
    }
    fun visiblePassword(isVisiblePassword: Boolean) {
        formState = formState.copy(isVisiblePassword = isVisiblePassword)
    }

    fun passwordChanged(password: String) {
        formState = formState.copy(password = password)
    }

    fun emailChanged(email: String) {
        formState = formState.copy(email = email)
    }

    private fun validatePasswordAndUsers(): Boolean {
        if (formState.password.isBlank()) {
            formState = formState.copy(passwordError = stringResourcesProvider.getString(R.string.error_password_empty))
            return false
        }

        formState = formState.copy(passwordError = null)
        return true
    }

    private fun validateEmail(): Boolean {

        if (formState.email.isBlank()) {
            formState = formState.copy(emailError = stringResourcesProvider.getString(R.string.error_email_empty))
            return false
        }

        if (!Regex("[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+").matches(formState.email)) {
            formState = formState.copy(emailError = stringResourcesProvider.getString(R.string.error_email_not_email))
            return false
        }


        formState = formState.copy(emailError = null)
        return true
    }

}