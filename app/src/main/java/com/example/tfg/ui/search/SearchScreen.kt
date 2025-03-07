package com.example.tfg.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tfg.ui.search.components.filterButtonsSearchForRow
import com.example.tfg.ui.search.components.filters
import com.example.tfg.ui.search.components.searchBarSearchScreen
import com.example.tfg.ui.search.components.searchItem
import com.example.tfg.ui.theme.TFGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchScreen(viewModel: SearchViewModel) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }


    TFGTheme(dynamicColor = false) {
        Scaffold() { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                searchBarSearchScreen {
                    showBottomSheet = true
                }
                filterButtonsSearchForRow()
                Box() {
                    Column(
                        Modifier
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy((10).dp)
                    ) {
                        for (result in viewModel.searchInfo.queryResult) {
                            searchItem(result)
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
