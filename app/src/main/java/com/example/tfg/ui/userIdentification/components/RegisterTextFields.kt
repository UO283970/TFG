package com.example.tfg.ui.userIdentification.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.userIdentification.RegisterMainEvent
import com.example.tfg.ui.userIdentification.RegisterViewModel

@Composable
fun textFieldUserName(registerViewModel: RegisterViewModel) {
    Column {
        OutlinedTextField(
            value = registerViewModel.formState.userName,
            onValueChange = { registerViewModel.onEvent(RegisterMainEvent.UserNameChanged(it)) },
            singleLine = true,
            label = { Text(stringResource(R.string.register_user_name_placeholder)) },
            trailingIcon = {
                if (registerViewModel.formState.userName != "") {
                    IconButton(onClick = { registerViewModel.formState.userName = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.text_field_delete))
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = registerViewModel.formState.userNameError != null
        )
        if (registerViewModel.formState.userNameError != null)
            Text(
                text = registerViewModel.formState.userNameError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
    }

}

@Composable
fun textFieldUserEmail(registerViewModel: RegisterViewModel) {
    Column {
        OutlinedTextField(
            value = registerViewModel.formState.email,
            onValueChange = { registerViewModel.onEvent(RegisterMainEvent.EmailChanged(it)) },
            singleLine = true,
            label = { Text(stringResource(R.string.register_user_email_placeholder)) },
            trailingIcon = {
                if (registerViewModel.formState.email != "") {
                    IconButton(onClick = { registerViewModel.formState.email = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.text_field_delete))
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = registerViewModel.formState.emailError != null
        )
        if (registerViewModel.formState.emailError != null)
            Text(
                text = registerViewModel.formState.emailError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
    }

}

@Composable
fun passwordRegisterTextField(registerViewModel: RegisterViewModel) {
    Column {
        OutlinedTextField(
            value = registerViewModel.formState.password,
            onValueChange = { registerViewModel.onEvent(RegisterMainEvent.PasswordChanged(it)) },
            singleLine = true,
            label = { Text(stringResource(R.string.login_pass_placeholder)) },
            trailingIcon = {
                Icon(
                    if (registerViewModel.formState.isVisiblePassword) {
                        painterResource(R.drawable.visibility_icon)
                    } else {
                        painterResource(R.drawable.visibility_off_icon)
                    },
                    contentDescription = "Toggle password visibility",
                    modifier = Modifier
                        .requiredSize(24.dp)
                        .clickable { registerViewModel.onEvent(RegisterMainEvent.VisiblePassword(!registerViewModel.formState.isVisiblePassword)) }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation =
            if (registerViewModel.formState.isVisiblePassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = registerViewModel.formState.passwordError != null
        )
        if (registerViewModel.formState.passwordError != null)
            Text(
                text = registerViewModel.formState.passwordError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
    }
}

@Composable
fun passwordRepeatRegisterTextField(registerViewModel: RegisterViewModel) {
    Column {
        OutlinedTextField(
            value = registerViewModel.formState.passwordRepeat,
            onValueChange = { registerViewModel.onEvent(RegisterMainEvent.PasswordRepeatChanged(it)) },
            singleLine = true,
            label = { Text(stringResource(R.string.login_pass_placeholder)) },
            trailingIcon = {
                Icon(
                    if (registerViewModel.formState.isVisiblePasswordRepeat) {
                        painterResource(R.drawable.visibility_icon)
                    } else {
                        painterResource(R.drawable.visibility_off_icon)
                    },
                    contentDescription = "Toggle password visibility",
                    modifier = Modifier
                        .requiredSize(24.dp)
                        .clickable {
                            registerViewModel.onEvent(RegisterMainEvent.VisiblePasswordRepeat(!registerViewModel.formState.isVisiblePasswordRepeat))
                        }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation =
            if (registerViewModel.formState.isVisiblePasswordRepeat) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = registerViewModel.formState.passwordRepeatError != null
        )
        if (registerViewModel.formState.passwordRepeatError != null)
            Text(
                text = registerViewModel.formState.passwordRepeatError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
    }
}