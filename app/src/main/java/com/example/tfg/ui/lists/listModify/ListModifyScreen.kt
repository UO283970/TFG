package com.example.tfg.ui.lists.listModify

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.lists.listCreation.DropDownMenu
import com.example.tfg.ui.lists.listCreation.ListDescriptionTextField
import com.example.tfg.ui.lists.listCreation.ListNameTextField
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun ListModifyScreen(
    returnToLastScreen: () -> Unit,
    viewModel: ListModifyViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel.listCreationState.listModify) {
        if(viewModel.listCreationState.listModify){
            returnToLastScreen()
        }
    }

    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                TopDetailsListBar(
                    returnToLastScreen,
                    stringResource(R.string.list_creation_modify_list)
                )
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .then(Modifier.padding(start = 10.dp, end = 10.dp))
            ) {
                ListNameTextField(viewModel.listCreationState.listName, { viewModel.changeListName(it) }, viewModel.listCreationState.listNameError)
                ListDescriptionTextField(
                    Modifier.weight(0.5f),
                    viewModel.listCreationState.listDescription,
                    { viewModel.changeListDesc(it) },
                    viewModel.listCreationState.listDescError
                )
                DropDownMenu(
                    viewModel.listCreationState.dropDawnExpanded,
                    { viewModel.changeDropDownState(it) },
                    { viewModel.listCreationState.listPrivacy.getListPrivacyString(it) },
                    viewModel.stringResourcesProvider
                ) { viewModel.changeListPrivacy(it) }
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