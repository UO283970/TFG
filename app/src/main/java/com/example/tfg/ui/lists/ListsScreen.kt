package com.example.tfg.ui.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.ui.lists.components.SearchBarListScreen
import com.example.tfg.ui.lists.components.TabsLists
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun ListScreen(
    navigateTo: (route: String) -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {
    val state by viewModel.listState.collectAsState()

    TFGTheme (dynamicColor = false){
        Scaffold { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                SearchBarListScreen(viewModel, state)
                TabsLists(viewModel, navigateTo, state)
            }
        }
    }
}
