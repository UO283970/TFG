package com.example.tfg.ui.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.model.booklist.BookList
import com.example.tfg.ui.lists.components.SearchBarListScreen
import com.example.tfg.ui.lists.components.TabsLists
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun ListScreen(
    navigateTo: (route: String) -> Unit,
    navigateToListDetails: (bookList: BookList) -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {
    TFGTheme {
        Scaffold { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                SearchBarListScreen(viewModel)
                TabsLists(viewModel, navigateTo, navigateToListDetails)
            }
        }
    }
}
