package com.example.tfg.ui.profile.components.configuration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.navHost.HomeRoutesItems
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun ConfigurationScreen(
    returnToPreviosScreen: () -> Unit,
    navigateTo: (route: String) -> Unit,
    viewModel: ConfigurationScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.confState.toLogin) {
        if(viewModel.confState.toLogin){
            navigateTo(HomeRoutesItems.LoginScreen.route)
        }
    }

    TFGTheme {
        Scaffold(topBar = {
            TopDetailsListBar(returnToPreviosScreen, stringResource(R.string.profile_configuration))
        }) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                Column {
                    ConfigurationRow({ navigateTo("") }, stringResource(R.string.profile_configuration_security), R.drawable.security_icon)
                    ConfigurationRow({ navigateTo("") }, stringResource(R.string.profile_configuration_notifications), R.drawable.notification_icon)
                    ConfigurationRow({viewModel.deleteAccount()}, stringResource(R.string.profile_configuration_delete_account), R.drawable.delete_icon)
                    ConfigurationRow({viewModel.logout()}, stringResource(R.string.profile_configuration_logout), R.drawable.logout_icon)
                }

            }
        }
    }
}

@Composable
private fun ConfigurationRow(
    mainOnClick: () -> Unit,
    mainTittle: String,
    mainIcon: Int
) {
    Row(
        modifier = Modifier
            .clickable { mainOnClick() }
            .padding(top = 10.dp, start = 5.dp, end = 5.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painterResource(mainIcon), mainTittle, modifier = Modifier.size(24.dp))
        Text(
            mainTittle,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        )
        Icon(Icons.AutoMirrored.Default.ArrowForward, stringResource(R.string.profile_configuration_go_to))
    }
}