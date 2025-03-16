package com.example.tfg.ui.userIdentification.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.common.navHost.HomeRoutesItems
import com.example.tfg.ui.common.navHost.Routes
import com.example.tfg.ui.userIdentification.LoginViewModel

@Composable
fun SubmitOrRegisterScreenButtons(
    loginViewModel: LoginViewModel,
    navigateTo: (route: String) -> Unit,
    navigateToWithoutSave: (route: String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if(loginViewModel.submit()){
                    navigateToWithoutSave(Routes.Home.route)
                }
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
                Modifier.clickable { navigateTo(HomeRoutesItems.RegisterScreen.route) })
        }
    }
}