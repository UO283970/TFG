package com.example.tfg.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.search.components.SearchBarSearchScreen
import com.example.tfg.ui.search.components.SearchItem
import com.example.tfg.ui.search.components.filterButtonsSearchForRow
import com.example.tfg.ui.search.components.filters
import com.example.tfg.ui.theme.TFGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navigateTo: (route: String) -> Unit,viewModel: SearchViewModel = hiltViewModel()) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }


    TFGTheme(dynamicColor = false) {
        Scaffold{ innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                SearchBarSearchScreen (viewModel){
                    showBottomSheet = true
                }
                filterButtonsSearchForRow()
                if (viewModel.searchInfo.queryResult.isEmpty() && viewModel.searchInfo.queryResult.isEmpty()){
                    Column (verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()){
                        Text(stringResource(R.string.search_start_message), textAlign = TextAlign.Center)
                    }
                }
                else{
                    Box{
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy((10).dp)
                        ) {
                            items(viewModel.searchInfo.queryResult){
                                SearchItem(it,navigateTo)
                            }
                        }

                    }
                    if (showBottomSheet) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                showBottomSheet = false
                            },
                            sheetState = sheetState,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        ) {
                            filters()
                        }
                    }
                }
            }
        }
    }
}
