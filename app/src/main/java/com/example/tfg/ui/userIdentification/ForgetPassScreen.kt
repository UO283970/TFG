package com.example.tfg.ui.userIdentification

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.ErrorText
import com.example.tfg.ui.theme.TFGTheme
import com.example.tfg.ui.userIdentification.components.LoginMainText

@Composable
fun ForgetPassScreen(loginViewModel: LoginViewModel = hiltViewModel()) {
    val state by loginViewModel.formState.collectAsState()
    val context = LocalContext.current

    TFGTheme(dynamicColor = false) {
        Scaffold { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .then(Modifier.padding(10.dp)), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                LoginMainText(stringResource(R.string.login_forget_pass_title_screen))
                Column {
                    OutlinedTextField(
                        value = state.emailPassReset,
                        onValueChange = { loginViewModel.emailPassResetChanged(it) },
                        singleLine = true,
                        label = { Text(stringResource(R.string.login_user_placeholder)) },
                        trailingIcon = {
                            if (state.emailPassReset != "") {
                                IconButton(onClick = { loginViewModel.emailPassResetChanged("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.text_field_delete))
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        isError = state.emailPassResetError != null,
                    )
                    if (state.emailPassResetError != null)
                        ErrorText(state.emailPassResetError!!)
                }
                val emailSendText = stringResource(R.string.login_forget_pass_email_send)
                Button({
                    loginViewModel.sendResetEmail()
                    if (state.showToast) {
                        Toast.makeText(context, emailSendText, Toast.LENGTH_SHORT).show()
                    }
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.login_forget_pass_send_email))
                }
            }
        }
    }
}