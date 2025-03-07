package com.example.tfg.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tfg.R
import com.example.tfg.ui.common.navHost.LoginRoutesItems
import com.example.tfg.ui.login.components.loginMainText
import com.example.tfg.ui.login.components.passwordTextField
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun registerScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    var checkErrors  by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf("") }

    TFGTheme(dynamicColor = false) {
        Scaffold { innerPadding ->
            Column(
                Modifier.padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)) {
                loginMainText(stringResource(R.string.login_register))
                Column(
                    Modifier.padding(start = 10.dp, end = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)) {
                    textFieldUserName()
                    textFieldUserEmail()
                    passwordTextField(loginViewModel)
                    passwordTextField(loginViewModel)
                    Column (verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally){
                        registerButton()
                        Row(horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically){
                            Text(stringResource(R.string.new_already_registered))
                            Text(
                                stringResource(R.string.login_here_button),
                                Modifier.clickable { navController.navigate(LoginRoutesItems.LoginScreen.route) })
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun registerButton(){
    Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(R.string.register_button))
    }
}

@Composable
fun textFieldUserName() {
    var userName by remember { mutableStateOf("") }

    OutlinedTextField(
        value = userName,
        onValueChange = { userName = it },
        singleLine = true,
        label = { Text(stringResource(R.string.register_user_name_placeholder)) },
        trailingIcon = {
            if (userName != "") {
                IconButton(onClick = { userName = "" }) {
                    Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.text_field_delete))
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        isError = userName.isNotEmpty()
    )
}

@Composable
fun textFieldUserEmail() {
    var userName by remember { mutableStateOf("") }

    OutlinedTextField(
        value = userName,
        onValueChange = { userName = it },
        singleLine = true,
        label = { Text(stringResource(R.string.register_user_email_placeholder)) },
        trailingIcon = {
            if (userName != "") {
                IconButton(onClick = { userName = "" }) {
                    Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.text_field_delete))
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        isError = userName.isNotEmpty()
    )
}

