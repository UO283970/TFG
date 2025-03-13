package com.example.tfg.ui.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.model.AppConstants
import com.example.tfg.model.booklist.ListPrivacy
import com.example.tfg.ui.common.errorText
import com.example.tfg.ui.lists.listDetails.components.topDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun newListCreationScreen(viewModel: ListCreationViewModel) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                topDetailsListBar(
                    viewModel.commonEventHandler,
                    stringResource(R.string.list_creation_new_list)
                )
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .then(Modifier.padding(start = 10.dp, end = 10.dp))
            ) {
                listNameTextField(viewModel)
                listDescriptionTextField(viewModel, Modifier.weight(0.5f))
                dropDownMenu(viewModel)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = { viewModel.saveNewList() }) {
                        Text(stringResource(R.string.save_button))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun dropDownMenu(viewModel: ListCreationViewModel) {
    Row(modifier = Modifier
        .clickable {
            viewModel.changeDropDownState(!viewModel.listCreationState.dropDawnExpanded)
        }
        .padding(top = 10.dp)) {
        Text(
            stringResource(R.string.list_creation_list_privacy) + " "
        )
        Row {
            Text(
                viewModel.listCreationState.listPrivacy.getListPrivacyString(viewModel.stringResourcesProvider)
            )
            if (viewModel.listCreationState.dropDawnExpanded) {
                Icon(
                    Icons.Default.KeyboardArrowUp,
                    stringResource(R.string.list_creation_dropdown_menu_down_arrow)
                )
            } else {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    stringResource(R.string.list_creation_dropdown_menu_down_arrow)
                )
            }
            DropdownMenu(
                viewModel.listCreationState.dropDawnExpanded,
                onDismissRequest = { viewModel.changeDropDownState(false) }) {
                for (privacy in ListPrivacy.values().asList()) {
                    DropdownMenuItem(
                        text = { Text(privacy.getListPrivacyString(viewModel.stringResourcesProvider)) },
                        onClick = {
                            viewModel.changeListPrivacy(privacy)
                            viewModel.changeDropDownState(false)
                        })
                }
            }
        }
    }
}

@Composable
private fun listNameTextField(viewModel: ListCreationViewModel) {
    OutlinedTextField(
        value = viewModel.listCreationState.listName,
        onValueChange = { viewModel.changeListName(it) },
        singleLine = true,
        label = { Text(stringResource(R.string.list_creation_list_name)) },
        trailingIcon = {
            if (viewModel.listCreationState.listName != "") {
                IconButton(onClick = { viewModel.changeListName("") }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = stringResource(R.string.text_field_delete)
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        isError = viewModel.listCreationState.listNameError != null
    )
    if (viewModel.listCreationState.listNameError != null)
        errorText(viewModel.listCreationState.listNameError!!)
}

@Composable
private fun listDescriptionTextField(viewModel: ListCreationViewModel, weight: Modifier) {
    OutlinedTextField(
        value = viewModel.listCreationState.listDescription,
        onValueChange = { viewModel.changeListDesc(it) },
        label = { Text(stringResource(R.string.list_creation_list_desccription)) },
        modifier = Modifier
            .fillMaxWidth()
            .then(weight)
    )
    Text(
        text = stringResource(
            R.string.list_creation_list_desccription_char_count,
            viewModel.listCreationState.listDescription.length,
            AppConstants.LIST_DESC_MAX_CHARACTERS
        ),
        style = MaterialTheme.typography.bodySmall
    )
}

