package com.example.tfg.ui.userIdentification.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.tfg.ui.common.ErrorText
import com.example.tfg.ui.userIdentification.register.RegisterViewModel

@Composable
fun TextFieldUserAlias(registerViewModel: RegisterViewModel) {
    Column {
        OutlinedTextField(
            value = registerViewModel.formState.userAlias,
            onValueChange = { registerViewModel.userAliasChanged(it) },
            singleLine = true,
            label = { Text(stringResource(R.string.register_user_alias_placeholder)) },
            trailingIcon = {
                if (registerViewModel.formState.userAlias != "") {
                    IconButton(onClick = {registerViewModel.userAliasChanged("") }) {
                        Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.text_field_delete))
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = registerViewModel.formState.userAliasError != null,
            supportingText = { Text(stringResource(R.string.register_user_alias_supporting)) }
        )
        if (registerViewModel.formState.userAliasError != null)
            ErrorText(registerViewModel.formState.userAliasError!!)
    }
}

@Composable
fun TextFieldUserName(registerViewModel: RegisterViewModel) {
    Column {
        OutlinedTextField(
            value = registerViewModel.formState.userName,
            onValueChange = { registerViewModel.userNameChanged(it) },
            singleLine = true,
            label = { Text(stringResource(R.string.register_user_name_placeholder)) },
            trailingIcon = {
                if (registerViewModel.formState.userName != "") {
                    IconButton(onClick = { registerViewModel.userNameChanged("")}) {
                        Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.text_field_delete))
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = registerViewModel.formState.userNameError != null,
            supportingText = { Text(stringResource(R.string.register_user_name_supporting)) }
        )
        if (registerViewModel.formState.userNameError != null)
            ErrorText(registerViewModel.formState.userNameError!!)
    }
}

@Composable
fun TextFieldUserEmail(registerViewModel: RegisterViewModel) {
    Column {
        OutlinedTextField(
            value = registerViewModel.formState.email,
            onValueChange = { registerViewModel.emailChanged(it) },
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
            ErrorText(registerViewModel.formState.emailError!!)
    }

}

@Composable
fun PasswordRegisterTextField(registerViewModel: RegisterViewModel) {
    Column {
        OutlinedTextField(
            value = registerViewModel.formState.password,
            onValueChange = { registerViewModel.passwordChanged(it) },
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
                        .clickable { registerViewModel.visiblePassword(!registerViewModel.formState.isVisiblePassword) }
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
            ErrorText(registerViewModel.formState.passwordError!!)
    }
}

@Composable
fun PasswordRepeatRegisterTextField(registerViewModel: RegisterViewModel) {
    Column {
        OutlinedTextField(
            value = registerViewModel.formState.passwordRepeat,
            onValueChange = { registerViewModel.passwordRepeatChanged(it) },
            singleLine = true,
            label = { Text(stringResource(R.string.login_pass_repeat_placeholder)) },
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
                            registerViewModel.visiblePasswordRepeat(!registerViewModel.formState.isVisiblePasswordRepeat)
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
            ErrorText(registerViewModel.formState.passwordRepeatError!!)
    }
}