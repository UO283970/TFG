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
import com.example.tfg.ui.userIdentification.LoginMainState
import com.example.tfg.ui.userIdentification.LoginViewModel

@Composable
fun TextFieldUserEmail(viewModel: LoginViewModel, state: LoginMainState){
    Column {
        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.emailChanged(it) },
            singleLine = true,
            label = { Text(stringResource(R.string.login_user_placeholder)) },
            trailingIcon = {
                if (state.email != "") {
                    IconButton(onClick = { viewModel.emailChanged("") }) {
                        Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.text_field_delete))
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = state.emailError != null,
        )
        if(state.emailError != null)
            ErrorText(state.emailError!!)
    }


}

@Composable
fun PasswordTextField(viewModel: LoginViewModel, state: LoginMainState) {
    Column {
        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.passwordChanged(it) },
            singleLine = true,
            label = { Text(stringResource(R.string.login_pass_placeholder)) },
            trailingIcon = {
                Icon(
                    if (state.isVisiblePassword) {
                        painterResource(R.drawable.visibility_icon)
                    } else {
                        painterResource(R.drawable.visibility_off_icon)
                    },
                    contentDescription = "Toggle password visibility",
                    modifier = Modifier
                        .requiredSize(24.dp)
                        .clickable { viewModel.visiblePassword(!state.isVisiblePassword) }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation =
            if (state.isVisiblePassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = state.passwordError != null,
        )
        if(state.passwordError != null)
            ErrorText(state.passwordError!!)

    }
}