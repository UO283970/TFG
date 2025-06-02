package com.example.tfg.ui.profile.components.editScreen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.signature.ObjectKey
import com.example.tfg.R
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.theme.TFGTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalPermissionsApi::class)
@Composable
fun EditScreen(
    returnToLastScreen: () -> Unit,
    editProfileViewModel: EditProfileViewModel = hiltViewModel()
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(editProfileViewModel.profileEditState.profileEdited) {
        if(editProfileViewModel.profileEditState.profileEdited){
            returnToLastScreen()
        }
    }

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        Manifest.permission.READ_MEDIA_IMAGES
    else
        Manifest.permission.READ_EXTERNAL_STORAGE

    val permissionState = rememberPermissionState(permission)

    var imageUri by remember {mutableStateOf<Uri?>(editProfileViewModel.profileEditState.userImageUri)}

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
                editProfileViewModel.setImageUri(it)
            }
        }
    )

    var snackBarString = stringResource(R.string.snackBar_text)
    var actionLabel = stringResource(R.string.snackBar_actionLabel)


    val context = LocalContext.current
    val signatureKey = remember { mutableStateOf(System.currentTimeMillis().toString()) }

    LaunchedEffect(editProfileViewModel.profileEditState.showDialog) {
        if (!permissionState.status.isGranted && editProfileViewModel.profileEditState.checkGalleryPermission) {
            permissionState.launchPermissionRequest()
        }
    }

    TFGTheme(dynamicColor = false)
    {

        Scaffold(
            topBar = {
                TopDetailsListBar(
                    returnToLastScreen,
                    stringResource(R.string.edit_profile)
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
            ) {
                HorizontalDivider()
                Column(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                        {editProfileViewModel.changeDialog()},
                        {editProfileViewModel.changePermission()}
                    )
                    Column(modifier = Modifier.padding(start = 5.dp)) {
                        EditProfileEmailTextField(editProfileViewModel)
                        EditProfileUserAliasTextField(editProfileViewModel)
                        EditProfileUserNameTextField(editProfileViewModel)
                        EditProfileUserDescriptionTextField(editProfileViewModel, Modifier.weight(1f))
                        ProfileEditSwitch(editProfileViewModel)
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Button(onClick = {
                                editProfileViewModel.saveButtonOnClick()
                            }) {
                                Text(stringResource(R.string.save_button))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalPermissionsApi::class)
 fun ChangeProfileImage(
    imageUri: Uri?,
    signatureKey: MutableState<String>,
    permissionState: PermissionState,
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    snackBarString: String,
    actionLabel: String,
    context: Context,
    changeDialog: () -> Unit,
    changePermission: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        GlideImage(
            model = imageUri ?: "",
            contentDescription = stringResource(R.string.profile_picture),
            loading = placeholder(R.drawable.default_user_image),
            failure = placeholder(R.drawable.default_user_image),
            transition = CrossFade,
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        ) {
            it.signature(ObjectKey(signatureKey.value))
        }
        Text(
            stringResource(R.string.edit_change_profile_image),
            modifier = Modifier.clickable {
                if (permissionState.status.isGranted) {
                    galleryLauncher.launch("image/*")
                } else {
                    changeDialog()
                    changePermission()
                    if (!permissionState.status.shouldShowRationale) {
                        scope.launch {
                            val result = snackBarHostState.showSnackbar(
                                message = snackBarString,
                                actionLabel = actionLabel,
                                duration = SnackbarDuration.Short
                            )
                            when (result) {
                                SnackbarResult.Dismissed -> ""
                                SnackbarResult.ActionPerformed -> {
                                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                        data = Uri.fromParts("package", context.packageName, null)
                                    }
                                    context.startActivity(intent)
                                }
                            }
                        }
                    }
                }
            })
    }
}


