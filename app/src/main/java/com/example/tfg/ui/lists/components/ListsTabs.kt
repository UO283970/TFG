package com.example.tfg.ui.lists.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalFocusManager
import com.example.tfg.ui.lists.ListMainState
import com.example.tfg.ui.lists.ListViewModel

@Composable
fun TabsLists(
    viewModel: ListViewModel,
    navigateTo: (route: String) -> Unit,
    state: ListMainState
) {
    var localFocus = LocalFocusManager.current

    TabRow(
        selectedTabIndex = state.tabIndex,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        state.tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(title) },
                selected = state.tabIndex == index,
                onClick = {
                    localFocus.clearFocus(true)
                    viewModel.tabChange(index)
                    viewModel.searchList()
                })
        }
    }
    when (state.tabIndex) {
        0 -> CreteOwnLists(viewModel, navigateTo, state)
        1 -> CreteDefaultLists(state, navigateTo, viewModel)
    }
}