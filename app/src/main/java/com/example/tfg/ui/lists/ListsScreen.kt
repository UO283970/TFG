package com.example.tfg.ui.lists

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tfg.ui.lists.components.searchBarListScreen
import com.example.tfg.ui.lists.components.tabsLists
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun listScreen(navController: NavHostController){
    TFGTheme {
        Scaffold() { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                searchBarListScreen()
                tabsLists(navController)
            }
        }
    }
}

@Preview(name = "LightMode", showBackground = true, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode", showSystemUi = true)
@Composable
fun previewCompsLists() {
    val navController = rememberNavController()
    ListNavHost(navController)
}