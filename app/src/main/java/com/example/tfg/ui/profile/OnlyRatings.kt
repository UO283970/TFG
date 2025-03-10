package com.example.tfg.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tfg.ui.friends.components.friendActivityItem
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun onlyRatings(navController: NavController) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                //topDetailsListBar(navController = navController, tittle = stringResource(R.string.profile_rating_text))
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                HorizontalDivider(Modifier.padding(bottom = 10.dp))
                friendActivityItem(arrayListOf())
            }
        }
    }
}

