package com.example.tfg.ui.userIdentification

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.ChargingProgress
import com.example.tfg.ui.common.navHost.HomeRoutesItems
import com.example.tfg.ui.common.navHost.Routes
import com.example.tfg.ui.theme.TFGTheme
import com.example.tfg.ui.userIdentification.components.LoginMainText
import com.example.tfg.ui.userIdentification.components.PasswordTextField
import com.example.tfg.ui.userIdentification.components.SubmitOrRegisterScreenButtons
import com.example.tfg.ui.userIdentification.components.TextFieldUserEmail

@Composable
fun LoginScreen(
    navigateTo: (route: String) -> Unit,
    navigateToWithoutSave: (route: String) -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val state by loginViewModel.formState.collectAsState()

    LaunchedEffect(state.userIsLoggedIn) {
        if (state.userIsLoggedIn) {
            navigateToWithoutSave(Routes.Home.route)
        }
    }

    if (!state.chargingInfo && !state.userIsLoggedIn) {
        TFGTheme(dynamicColor = false) {
            Scaffold { innerPadding ->
                Column(
                    Modifier.padding(innerPadding)
                ) {
                    Column(
                        modifier = Modifier.padding(top = 5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Column ( horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy((-20).dp)){
                            Image(painterResource(R.drawable.main_app_icon),null, modifier = Modifier.fillMaxSize(0.15f))
                            LoginMainText(stringResource(R.string.login_welcome))
                        }
                        Column(
                            Modifier.padding(start = 10.dp, end = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            TextFieldUserEmail(loginViewModel, state)
                            PasswordTextField(loginViewModel, state)
                            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
                                Text(stringResource(R.string.login_forget_pass), modifier = Modifier.clickable{
                                    navigateTo(HomeRoutesItems.PasswordReset.route)
                                })
                            }
                            SubmitOrRegisterScreenButtons(loginViewModel, navigateTo)
                        }
                    }
                }
            }
        }
    } else {
        ChargingProgress()
    }
}

