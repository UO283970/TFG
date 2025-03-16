package com.example.tfg.ui.userIdentification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.theme.TFGTheme
import com.example.tfg.ui.userIdentification.components.PasswordTextField
import com.example.tfg.ui.userIdentification.components.SubmitOrRegisterScreenButtons
import com.example.tfg.ui.userIdentification.components.TextFieldUserEmail
import com.example.tfg.ui.userIdentification.components.loginMainText

@Composable
fun LoginScreen(
    navigateTo: (route: String) -> Unit,
    navigateToWithoutSave: (route: String) -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val state by loginViewModel.formState.collectAsState()

    TFGTheme(dynamicColor = false) {
        Scaffold { innerPadding ->
            Column(
                Modifier.padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier.padding(top = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    loginMainText(stringResource(R.string.login_welcome))
                    Column(
                        Modifier.padding(start = 10.dp, end = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        TextFieldUserEmail(loginViewModel,state)
                        PasswordTextField(loginViewModel,state)
                        SubmitOrRegisterScreenButtons(loginViewModel, navigateTo, navigateToWithoutSave)
                    }
                }
            }
        }
    }
}

