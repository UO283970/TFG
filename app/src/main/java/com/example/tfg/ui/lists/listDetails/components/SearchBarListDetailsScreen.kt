package com.example.tfg.ui.lists.listDetails.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import com.example.tfg.R
import com.example.tfg.ui.lists.listDetails.ListDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchBarListDetailsScreen(viewModel: ListDetailsViewModel) {
    SearchBar(
        modifier = Modifier
            .semantics { traversalIndex = 0f }
            .fillMaxWidth(),
        inputField = {
            SearchBarDefaults.InputField(
                onSearch = { },
                expanded = false,
                onExpandedChange = { },
                placeholder = { Text(stringResource(id = R.string.search_placeholder_input)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "") },
                query = viewModel.listDetailsInfo.userQuery,
                onQueryChange = { viewModel.userQueryChange(it) }
            )
        },
        expanded = false,
        onExpandedChange = { },
    ) {}
}