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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.theme.TFGTheme
import com.example.tfg.ui.userIdentification.components.loginMainText
import com.example.tfg.ui.userIdentification.components.passwordTextField
import com.example.tfg.ui.userIdentification.components.textFieldUserEmail

@Composable
fun loginScreen(loginViewModel: LoginViewModel) {
    TFGTheme(dynamicColor = false) {
        Scaffold { innerPadding ->
            Column(
                Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                loginMainText(stringResource(R.string.login_welcome))
                Column(
                    Modifier.padding(start = 10.dp, end = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    textFieldUserEmail(loginViewModel)
                    passwordTextField(loginViewModel)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                      loginViewModel.onEvent(LoginMainEvent.Submit)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.login_button))
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(stringResource(R.string.new_user_question))
                            Text(stringResource(R.string.regiter_here_button),
                                Modifier.clickable { loginViewModel.onEvent(LoginMainEvent.NavigateToRegister) })
                        }
                    }

                }
            }
        }
    }
}
