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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.ui.common.descText
import com.example.tfg.ui.profile.components.editButton
import com.example.tfg.ui.profile.components.mainUserProfileInfo
import com.example.tfg.ui.profile.components.profileLists
import com.example.tfg.ui.theme.TFGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun profileScreen(viewModel: ProfileViewModel) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                TopAppBar(title = {Text(viewModel.profileInfo.user.userName.trim(),
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold)},
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Outlined.Settings, stringResource(R.string.settings_button))
                        }
                    }
                )
            }){ innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())) {
                Column(Modifier.padding(start = 10.dp, end = 5.dp)) {
                    mainUserProfileInfo(viewModel)
                    if(viewModel.profileInfo.user.description.trim() != ""){
                        descText(3,viewModel.profileInfo.user.description.trim())
                    }
                    editButton(viewModel)
                    profileLists(viewModel)
                }

            }
        }
    }
}