package com.example.tfg.ui.lists.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.tfg.model.booklist.BookList
import com.example.tfg.ui.lists.ListMainState
import com.example.tfg.ui.lists.ListViewModel

@Composable
fun TabsLists(
    viewModel: ListViewModel,
    navigateTo: (route: String) -> Unit,
    navigateToListDetails: (bookList: BookList) -> Unit,
    state: ListMainState
) {
    TabRow(
        selectedTabIndex = state.tabIndex,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        state.tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = state.tabIndex == index,
                onClick = { viewModel.tabChange(index) })
        }
    }
    when (state.tabIndex) {
        0 -> CreteOwnLists(viewModel, navigateTo, navigateToListDetails, state)
        1 -> CreteDefaultLists(viewModel, state, navigateToListDetails)
    }
}