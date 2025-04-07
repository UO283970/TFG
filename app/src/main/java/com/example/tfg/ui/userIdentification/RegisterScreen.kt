package com.example.tfg.ui.userIdentification

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.navHost.HomeRoutesItems
import com.example.tfg.ui.common.navHost.Routes
import com.example.tfg.ui.theme.TFGTheme
import com.example.tfg.ui.userIdentification.components.PasswordRegisterTextField
import com.example.tfg.ui.userIdentification.components.PasswordRepeatRegisterTextField
import com.example.tfg.ui.userIdentification.components.TextFieldUserEmail
import com.example.tfg.ui.userIdentification.components.TextFieldUserName
import com.example.tfg.ui.userIdentification.components.loginMainText

@Composable
fun RegisterScreen(
    navigateTo: (route: String) -> Unit,
    navigateToWithoutSave: (route: String) -> Unit,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    LaunchedEffect(registerViewModel.formState.isUserRegistered) {
        if (registerViewModel.formState.isUserRegistered) {
            navigateToWithoutSave(Routes.Home.route)
        }
    }

    TFGTheme(dynamicColor = false) {
        Scaffold { innerPadding ->
            Column(
                Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                Column(
                    Modifier.padding(top = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    loginMainText(stringResource(R.string.login_register))
                    Column(
                        Modifier.padding(start = 10.dp, end = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        TextFieldUserName(registerViewModel)
                        TextFieldUserEmail(registerViewModel)
                        PasswordRegisterTextField(registerViewModel)
                        PasswordRepeatRegisterTextField(registerViewModel)
                        Column(
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            RegisterButton(registerViewModel)
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(stringResource(R.string.new_already_registered))
                                Text(
                                    stringResource(R.string.login_here_button),
                                    Modifier.clickable { navigateTo(HomeRoutesItems.LoginScreen.route) })
                            }
                        }

                    }
                }

            }
        }
    }
}

@Composable
fun RegisterButton(registerViewModel: RegisterViewModel) {
    Button(onClick = {
        registerViewModel.submit()
    }, modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(R.string.register_button))
    }
}

