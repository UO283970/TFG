package com.example.tfg.ui.userIdentification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.tfg.R
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.common.navHost.HomeRoutesItems
import com.example.tfg.ui.common.navHost.Routes

sealed class LoginMainEvent {
    data class EmailChanged(val email: String) : LoginMainEvent()
    data class PasswordChanged(val password: String) : LoginMainEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : LoginMainEvent()
    object Submit : LoginMainEvent()
    object NavigateToRegister : LoginMainEvent()
}

data class LoginMainState(
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

    var formState by mutableStateOf(LoginMainState())

    fun onEvent(event: LoginMainEvent) {
        when (event) {
            is LoginMainEvent.NavigateToRegister -> {
                navController.navigate(HomeRoutesItems.RegisterScreen.route)
            }
            is LoginMainEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }

            is LoginMainEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
            }

            is LoginMainEvent.VisiblePassword -> {
                formState = formState.copy(isVisiblePassword = event.isVisiblePassword)
            }

            is LoginMainEvent.Submit -> {
                val correctEmail = validateEmail()
                val correctUser = validatePasswordAndUsers()
                if (correctEmail && correctUser) {
                    navController.navigate(Routes.Home.route,){
                        popUpTo(HomeRoutesItems.HomeScreen.route) { inclusive = true }
                    }
                }
            }
        }
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