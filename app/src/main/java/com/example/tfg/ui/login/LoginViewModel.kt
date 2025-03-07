package com.example.tfg.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.tfg.R
import com.example.tfg.StringResourcesProvider
import com.example.tfg.ui.common.navHost.Routes

sealed class MainEvent {
    data class EmailChanged(val email: String) : MainEvent()
    data class PasswordChanged(val password: String) : MainEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : MainEvent()
    object Submit : MainEvent()
}

data class MainState(
    var email: String = "",
    var emailError: String? = null,
    var password: String = "",
    var passwordError: String? = null,
    var isVisiblePassword: Boolean = false
)

class LoginViewModel(
    val navController: NavHostController,
    private val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {

    var formState by mutableStateOf(MainState())

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }

            is MainEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
            }

            is MainEvent.VisiblePassword -> {
                formState = formState.copy(isVisiblePassword = event.isVisiblePassword)
            }

            is MainEvent.Submit -> {
                var correctEmail = validateEmail()
                var correctUser = validatePasswordAndUsers()
                if (correctEmail && correctUser) {
                    navController.navigate(Routes.Home.route)
                }
            }
        }
    }

    private fun validatePasswordAndUsers(): Boolean {
        if (formState.password.isBlank()) {
            formState = formState.copy(password = stringResourcesProvider.getString(R.string.error_password_empty))
            return false
        }

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

        return true
    }

}