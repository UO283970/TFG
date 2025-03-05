package com.example.tfg.ui.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.common.smallTittleText

@Composable
fun pagesFilter() {
    var minPags by remember { mutableStateOf("") }
    var maxPags by remember { mutableStateOf("") }

    smallTittleText(stringResource(id = R.string.search_filters_pagesFilter))
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        OutlinedTextField(value = minPags,
            onValueChange = { minPags = it },
            modifier = Modifier.width(160.dp),
            label = { Text(stringResource(id = R.string.search_filters_placeholder_min_pages)) },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { minPags = "" }
                ) {
                    Icon(Icons.Default.Clear, contentDescription = "")
                }
            }
        )
        OutlinedTextField(
            value = maxPags,
            modifier = Modifier.width(160.dp),
            onValueChange = { maxPags = it },
            label = { Text(stringResource(id = R.string.search_filters_placeholder_max_pages)) },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { maxPags = "" }) {
                    Icon(Icons.Default.Clear, contentDescription = "")
                }
            }
        )
    }
}