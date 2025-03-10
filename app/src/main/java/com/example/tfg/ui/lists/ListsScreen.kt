package com.example.tfg.ui.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tfg.ui.lists.components.searchBarListScreen
import com.example.tfg.ui.lists.components.tabsLists
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun listScreen(viewModel: ListViewModel){
    TFGTheme {
        Scaffold { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                searchBarListScreen(viewModel)
                tabsLists(viewModel)
            }
        }
    }
}
