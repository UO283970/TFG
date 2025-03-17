package com.example.tfg.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.DescText
import com.example.tfg.ui.common.navHost.ProfileNavigationItems
import com.example.tfg.ui.profile.components.EditButton
import com.example.tfg.ui.profile.components.MainUserProfileInfo
import com.example.tfg.ui.profile.components.ProfileLists
import com.example.tfg.ui.theme.TFGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navigateTo: (route: String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            viewModel.profileInfo.user?.userAlias?.trim() ?: "",
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            fontSize = 22.sp
                        )
                    },
                    actions = {
                        IconButton(onClick = { navigateTo(ProfileNavigationItems.ProfileConfiguration.route) }) {
                            Icon(Icons.Outlined.Settings, stringResource(R.string.settings_button))
                        }
                    }
                )
            }) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Column(Modifier.padding(start = 10.dp, end = 5.dp)) {
                    MainUserProfileInfo(viewModel.profileInfo.user, navigateTo)
                    if (viewModel.profileInfo.user?.description?.trim() != "") {
                        DescText(3, viewModel.profileInfo.user?.description?.trim() ?: "")
                    }
                    EditButton(navigateTo)
                    ProfileLists(viewModel.profileInfo.profileDefaultLists,viewModel.profileInfo.profileBookLists)
                }

            }
        }
    }
}