package com.example.tfg.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tfg.ui.common.descText
import com.example.tfg.ui.profile.components.editButton
import com.example.tfg.ui.profile.components.mainUserProfileInfo
import com.example.tfg.ui.profile.components.profileLists
import com.example.tfg.ui.profile.components.statistics
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun profileScreen() {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
        ) { innerPadding ->
            Column(Modifier.padding(innerPadding).verticalScroll(rememberScrollState())) {
                Column(Modifier.padding(start = 10.dp, top = 10.dp, end = 5.dp)) {
                    mainUserProfileInfo()
                    descText(3)
                    editButton()
                    statistics()
                    profileLists()
                }

            }
        }
    }
}


@Preview(name = "LightMode", showBackground = true, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode", showSystemUi = true)
@Composable
fun previewCompsProfile() {
    profileScreen()
}