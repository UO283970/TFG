package com.example.tfg.ui.lists.listDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.ui.common.DescText
import com.example.tfg.ui.lists.listDetails.components.ListDetailsItemList
import com.example.tfg.ui.lists.listDetails.components.SearchBarListDetailsScreen
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun ListDetailsScreen(returnToLastScreen: () -> Unit, viewModel: ListDetailsViewModel = hiltViewModel()) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                TopDetailsListBar(
                    returnToLastScreen,
                    viewModel.listDetailsInfo.bookList?.listName ?: ""
                )
            }
        ) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                HorizontalDivider(thickness = 1.dp)
                Column {
                    SearchBarListDetailsScreen(viewModel)
                    if (viewModel.listDetailsInfo.bookList?.listDescription != "") {
                        DescText(3, viewModel.listDetailsInfo.bookList?.listDescription ?: "")
                        HorizontalDivider(Modifier.padding(top = 5.dp))
                    }
                    ListDetailsItemList(viewModel.listDetailsInfo.bookList?.books ?: arrayListOf())
                }
            }
        }
    }
}
