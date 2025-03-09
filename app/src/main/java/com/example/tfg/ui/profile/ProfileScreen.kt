package com.example.tfg.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tfg.ui.common.descText
import com.example.tfg.ui.profile.components.editButton
import com.example.tfg.ui.profile.components.mainUserProfileInfo
import com.example.tfg.ui.profile.components.profileLists
import com.example.tfg.ui.profile.components.statistics
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun profileScreen(viewModel: ProfileViewModel) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
        ) { innerPadding ->
            Column(Modifier.padding(innerPadding).verticalScroll(rememberScrollState())) {
                Column(Modifier.padding(start = 10.dp, top = 10.dp, end = 5.dp)) {
                    mainUserProfileInfo(viewModel.profileInfo.user)
                    if(viewModel.profileInfo.user.description != ""){
                        descText(3,viewModel.profileInfo.user.description)
                    }
                    editButton(viewModel)
                    statistics(viewModel)
                    profileLists(viewModel)
                }

            }
        }
    }
}