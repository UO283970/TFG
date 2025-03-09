package com.example.tfg.ui.lists.listDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tfg.ui.common.descText
import com.example.tfg.ui.lists.listDetails.components.listDetailsItemList
import com.example.tfg.ui.lists.listDetails.components.searchBarListDetailsScreen
import com.example.tfg.ui.lists.listDetails.components.topDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun ListDetailsScreen(viewModel: ListDetailsViewModel) {
    TFGTheme(dynamicColor = false)
    {
        Scaffold(
            topBar = {
                 topDetailsListBar(viewModel,viewModel.listDetailsInfo.bookList.listName)
            }
        ) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                HorizontalDivider(thickness = 1.dp)
                Column {
                    searchBarListDetailsScreen(viewModel)
                    if(viewModel.listDetailsInfo.bookList.listDescription != ""){
                        descText(3, viewModel.listDetailsInfo.bookList.listDescription)
                        HorizontalDivider(Modifier.padding(top = 5.dp))
                    }
                    listDetailsItemList(viewModel.listDetailsInfo.bookList.books)
                }
            }
        }
    }
}
