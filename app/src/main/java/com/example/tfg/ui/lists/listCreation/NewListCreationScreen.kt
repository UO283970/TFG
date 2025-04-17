package com.example.tfg.ui.lists.listCreation

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.model.AppConstants
import com.example.tfg.model.booklist.ListPrivacy
import com.example.tfg.ui.common.ErrorText
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun NewListCreationScreen(
    returnToLastScreen: () -> Unit,
    viewModel: ListCreationViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.listCreationState.listCreated) {
        if (viewModel.listCreationState.listCreated) {
            returnToLastScreen()
        }
    }

    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                TopDetailsListBar(
                    returnToLastScreen,
                    stringResource(R.string.list_creation_new_list)
                )
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .then(Modifier.padding(start = 10.dp, end = 10.dp))
            ) {
                ListNameTextField(viewModel.listCreationState.listName, { viewModel.changeListName(it) }, viewModel.listCreationState.listNameError)
                ListDescriptionTextField(Modifier.weight(0.5f), viewModel.listCreationState.listDescription, { viewModel.changeListDesc(it) })
                DropDownMenu(
                    viewModel.listCreationState.dropDawnExpanded,
                    { viewModel.changeDropDownState(it) },
                    { viewModel.listCreationState.listPrivacy.getListPrivacyString(it) },
                    viewModel.stringResourcesProvider,
                    { viewModel.changeListPrivacy(it) })
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(onClick = {
                        viewModel.saveNewList()
                    }) {
                        Text(stringResource(R.string.save_button))
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun DropDownMenu(
    dropDawnExpanded: Boolean,
    changeDropDownState: (change: Boolean) -> Unit,
    getListPrivacyString: (stringResourcesProvider: StringResourcesProvider) -> String,
    stringResourcesProvider: StringResourcesProvider,
    changeListPrivacy: (privacy: ListPrivacy) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                changeDropDownState(!dropDawnExpanded)
            }
            .padding(top = 10.dp)) {
        Text(
            stringResource(R.string.list_creation_list_privacy) + " "
        )
        Row {
            Text(
                getListPrivacyString(stringResourcesProvider)
            )
            if (dropDawnExpanded) {
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
                dropDawnExpanded,
                onDismissRequest = { changeDropDownState(false) }) {
                for (privacy in ListPrivacy.entries) {
                    DropdownMenuItem(
                        text = { Text(privacy.getListPrivacyString(stringResourcesProvider)) },
                        onClick = {
                            changeListPrivacy(privacy)
                            changeDropDownState(false)
                        })
                }
            }
        }
    }
}

@Composable
fun ListNameTextField(listName: String, changeListName: (newName: String) -> Unit, listNameError: String?) {
    OutlinedTextField(
        value = listName,
        onValueChange = { changeListName(it) },
        singleLine = true,
        label = { Text(stringResource(R.string.list_creation_list_name)) },
        trailingIcon = {
            if (listName != "") {
                IconButton(onClick = { changeListName("") }) {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = stringResource(R.string.text_field_delete)
                    )
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        isError = listNameError != null
    )
    if (listNameError != null)
        ErrorText(listNameError)
}

@Composable
fun ListDescriptionTextField(modifier: Modifier, listDescription: String, changeListDesc: (newDesc: String) -> Unit) {
    OutlinedTextField(
        value = listDescription,
        onValueChange = { changeListDesc(it) },
        label = { Text(stringResource(R.string.list_creation_list_desccription)) },
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    )
    Text(
        text = stringResource(
            R.string.list_creation_list_desccription_char_count,
            listDescription.length,
            AppConstants.LIST_DESC_MAX_CHARACTERS
        ),
        style = MaterialTheme.typography.bodySmall
    )
}

