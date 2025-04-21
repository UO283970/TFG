package com.example.tfg.ui.userIdentification

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.navHost.Routes
import com.example.tfg.ui.profile.components.editScreen.ChangeProfileImage
import com.example.tfg.ui.theme.TFGTheme
import com.example.tfg.ui.userIdentification.components.LoginMainText
import com.example.tfg.ui.userIdentification.components.TextFieldUserAlias
import com.example.tfg.ui.userIdentification.components.TextFieldUserName
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RegisterImageSelector(navigateToWithoutSave: (route: String) -> Unit, registerViewModel: RegisterViewModel = hiltViewModel()) {
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        Manifest.permission.READ_MEDIA_IMAGES
    else
        Manifest.permission.READ_EXTERNAL_STORAGE

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val permissionState = rememberPermissionState(permission)

    var imageUri by remember { mutableStateOf<Uri?>(registerViewModel.formState.userImageUri) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
                registerViewModel.setImageUri(it)
            }
        }
    )

    var snackBarString = stringResource(R.string.snackBar_text)
    var actionLabel = stringResource(R.string.snackBar_actionLabel)


    val context = LocalContext.current
    val signatureKey = remember { mutableStateOf(System.currentTimeMillis().toString()) }

    LaunchedEffect(registerViewModel.formState.showDialog) {
        if (!permissionState.status.isGranted && registerViewModel.formState.checkGalleryPermission) {
            permissionState.launchPermissionRequest()
        }
    }

    LaunchedEffect(registerViewModel.formState.isUserRegistered) {
        if (registerViewModel.formState.isUserRegistered) {
            navigateToWithoutSave(Routes.Home.route)
        }
    }


    TFGTheme(dynamicColor = false) {
        Scaffold { innerPadding ->
            Column(
                Modifier.padding(innerPadding).then(Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginMainText(stringResource(R.string.login_register_image_selector))
                Column (Modifier.padding(top = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    ChangeProfileImage(
                        imageUri,
                        signatureKey,
                        permissionState,
                        galleryLauncher,
                        scope,
                        snackBarHostState,
                        snackBarString,
                        actionLabel,
                        context,
                        { registerViewModel.changeDialog() },
                        { registerViewModel.changePermission() }
                    )
                    TextFieldUserAlias(registerViewModel)
                    TextFieldUserName(registerViewModel)
                    Button(onClick = {
                        registerViewModel.submit()
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text(stringResource(R.string.register_button))
                    }
                }
            }
        }
    }
}