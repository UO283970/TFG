package com.example.tfg.ui.search.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.search.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarSearchScreen(viewModel: SearchViewModel, onClick: () -> Unit) {
    val focus = LocalFocusManager.current
    SearchBar(
        modifier = Modifier
            .semantics { traversalIndex = 0f }
            .fillMaxWidth(),
        inputField = {
            SearchBarDefaults.InputField(
                onSearch = {
                    viewModel.getResultsFromQuery()
                    focus.clearFocus()
                },
                expanded = viewModel.searchInfo.expandedSearchBar,
                onExpandedChange = { },
                placeholder = { Text(stringResource(id = R.string.search_placeholder_input)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") },
                trailingIcon = {
                    IconButton(onClick = {
                        onClick()
                        focus.clearFocus()
                    }) {
                        Icon(painterResource(R.drawable.filter_icon), contentDescription = "", modifier = Modifier.size(26.dp))
                    }
                },
                query = viewModel.searchInfo.userQuery,
                onQueryChange = { viewModel.userQueryChange(it) }
            )
        },
        expanded = false,
        onExpandedChange = { },
    ) {}
}