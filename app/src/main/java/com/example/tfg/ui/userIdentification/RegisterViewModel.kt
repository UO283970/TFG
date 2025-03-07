package com.example.tfg.ui.userIdentification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.tfg.R
import com.example.tfg.StringResourcesProvider
import com.example.tfg.model.AppConstants
import com.example.tfg.ui.common.navHost.LoginRoutesItems
import com.example.tfg.ui.common.navHost.Routes

sealed class RegisterMainEvent {
    object NavigateToLogin : RegisterMainEvent()
    data class UserNameChanged(val userName: String) : RegisterMainEvent()
    data class EmailChanged(val email: String) : RegisterMainEvent()
    data class PasswordChanged(val password: String) : RegisterMainEvent()
    data class PasswordRepeatChanged(val repeatPassword: String) : RegisterMainEvent()
    data class VisiblePasswordRepeat(val isVisibleRepeatPassword: Boolean) : RegisterMainEvent()
    data class VisiblePassword(val isVisiblePassword: Boolean) : RegisterMainEvent()
    object Submit : RegisterMainEvent()
}

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
    var isVisiblePasswordRepeat: Boolean = false
)

class RegisterViewModel(
    val navController: NavHostController,
    private val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {

    var formState by mutableStateOf(RegisterMainState())

    fun onEvent(event: RegisterMainEvent) {
        when (event) {
            is RegisterMainEvent.NavigateToLogin -> {
                navController.navigate(LoginRoutesItems.LoginScreen.route)
            }

            is RegisterMainEvent.UserNameChanged -> {
                formState = formState.copy(userName = event.userName)
            }

            is RegisterMainEvent.EmailChanged -> {
                formState = formState.copy(email = event.email)
            }

            is RegisterMainEvent.PasswordChanged -> {
                formState = formState.copy(password = event.password)
            }

            is RegisterMainEvent.VisiblePassword -> {
                formState = formState.copy(isVisiblePassword = event.isVisiblePassword)
            }

            is RegisterMainEvent.PasswordRepeatChanged -> {
                formState = formState.copy(passwordRepeat = event.repeatPassword)
            }

            is RegisterMainEvent.VisiblePasswordRepeat -> {
                formState = formState.copy(isVisiblePasswordRepeat = event.isVisibleRepeatPassword)
            }

            is RegisterMainEvent.Submit -> {
                val correctEmail: Boolean = validateEmailRegister()
                val correctUser: Boolean = validateUsers()
                val correctPassword: Boolean = validatePassword()
                val correctRepeatPassword: Boolean = validateRepeatPassword()
                if (correctEmail && correctUser && correctPassword && correctRepeatPassword) {
                    navController.navigate(Routes.Home.route)
                }
            }
        }
    }

    private fun validateRepeatPassword(): Boolean {
        if (formState.passwordRepeat.isBlank()) {
            formState =
                formState.copy(passwordRepeatError = stringResourcesProvider.getString(R.string.error_passwordRepeat))
            return false
        }

        if (formState.passwordRepeat != formState.password) {
            formState =
                formState.copy(passwordRepeatError = stringResourcesProvider.getString(R.string.error_passwordRepeat))
            return false
        }

        formState = formState.copy(passwordRepeatError = null)
        return true
    }

    private fun validatePassword(): Boolean {
        if (formState.password.isBlank()) {
            formState = formState.copy(passwordError = stringResourcesProvider.getString(R.string.error_password_empty))
            return false
        }

        if (formState.password.length < AppConstants.PASS_MIN_CHARACTERS) {
            formState =
                formState.copy(
                    passwordError = stringResourcesProvider.getStringWithParameters(
                        R.string.error_password_length,
                        AppConstants.PASS_MIN_CHARACTERS
                    )
                )

            return false
        }

        val hasUpperCase = Regex("[A-Z]").containsMatchIn(formState.password)
        val hasLowerCase = Regex("[a-z]").containsMatchIn(formState.password)
        val hasNumbers = Regex("[1-9]").containsMatchIn(formState.password)
        val hasNonAlphas = Regex("\\w").containsMatchIn(formState.password)

        if (!(hasUpperCase && hasLowerCase && hasNumbers && hasNonAlphas)) {
            formState =
                formState.copy(passwordError = stringResourcesProvider.getString(R.string.error_password_rules))
            return false
        }

        if (formState.password.isBlank()) {
            formState = formState.copy(passwordError = stringResourcesProvider.getString(R.string.error_password_empty))
            return false
        }

        formState = formState.copy(passwordError = null)
        return true
    }

    private fun validateUsers(): Boolean {
        if (formState.userName.isBlank()) {
            formState = formState.copy(userNameError = stringResourcesProvider.getString(R.string.error_userName_empty))
            return false
        }

        /*TODO: Mirar que no exista ya el nombre de usuario*/

        formState = formState.copy(userNameError = null)
        return true
    }

    private fun validateEmailRegister(): Boolean {

        if (formState.email.isBlank()) {
            formState = formState.copy(emailError = stringResourcesProvider.getString(R.string.error_email_empty))
            return false
        }

        if (!Regex("[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+").matches(formState.email)) {
            formState = formState.copy(emailError = stringResourcesProvider.getString(R.string.error_email_not_email))
            return false
        }

        /*TODO: Mirar que no este registrado ya*/

        formState = formState.copy(emailError = null)
        return true
    }

}