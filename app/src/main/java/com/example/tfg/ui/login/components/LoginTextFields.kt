package com.example.tfg.ui.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.login.LoginViewModel
import com.example.tfg.ui.login.MainEvent

@Composable
fun textFieldUserEmail(viewModel: LoginViewModel){
    Column {
        OutlinedTextField(
            value = viewModel.formState.email,
            onValueChange = { viewModel.onEvent(MainEvent.EmailChanged(it)) },
            singleLine = true,
            label = { Text(stringResource(R.string.login_user_placeholder)) },
            trailingIcon = {
                if (viewModel.formState.email != "") {
                    IconButton(onClick = { viewModel.onEvent(MainEvent.EmailChanged("")) }) {
                        Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.text_field_delete))
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.formState.emailError != null,
        )
        if(viewModel.formState.emailError != null)
            Text(text = viewModel.formState.emailError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall)
    }


}

@Composable
fun passwordTextField(checkErrors: LoginViewModel): Boolean {
    var showPassword by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        singleLine = true,
        label = { Text(stringResource(R.string.login_pass_placeholder)) },
        trailingIcon = {
            Icon(
                if (showPassword) {
                    painterResource(R.drawable.visibility_icon)
                } else {
                    painterResource(R.drawable.visibility_off_icon)
                },
                contentDescription = "Toggle password visibility",
                modifier = Modifier
                    .requiredSize(24.dp)
                    .clickable { showPassword = !showPassword }
            )
        },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation =
        if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )

    return false
}