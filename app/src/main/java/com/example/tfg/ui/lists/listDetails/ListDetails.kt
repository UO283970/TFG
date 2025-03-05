package com.example.tfg.ui.lists.listDetails

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tfg.ui.common.descText
import com.example.tfg.ui.lists.listDetails.components.searchBarListDetailsScreen
import com.example.tfg.ui.lists.listDetails.components.topDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun ListDetails(navController: NavController, tittle: String?) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                if (tittle != null) {
                    topDetailsListBar(navController, tittle)
                }
            }
        ) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                searchBarListDetailsScreen()
                descText(3)
                HorizontalDivider(Modifier.padding(top = 5.dp))
                listDetailsItemList()
            }
        }
    }
}

@Preview(name = "LightMode", showBackground = true, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode", showSystemUi = true)
@Composable
fun previewCompsDetailLists() {
    ListDetails(navController = rememberNavController(), tittle = "Nadad")
}