package com.example.tfg.ui.search

import android.content.res.Configuration
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.ui.search.components.filterButtonsSearchForRow
import com.example.tfg.ui.search.components.filters
import com.example.tfg.ui.search.components.searchBarSearchScreen
import com.example.tfg.ui.search.components.searchItem
import com.example.tfg.ui.theme.TFGTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchScreen() {
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
                        searchItem(Book("Words Of Radiance", "Brandon Sanderson", painterResource(R.drawable.prueba), LocalDate.ofYearDay(2016,15), 923))
                        searchItem(Book("Words Of Radiance", "Brandon Sanderson", painterResource(R.drawable.prueba), LocalDate.ofYearDay(2016,15), 923))
                        searchItem(Book("Words Of Radiance", "Brandon Sanderson", painterResource(R.drawable.prueba), LocalDate.ofYearDay(2016,15), 923))
                        searchItem(Book("Words Of Radiance", "Brandon Sanderson", painterResource(R.drawable.prueba), LocalDate.ofYearDay(2016,15), 923))
                        searchItem(Book("Words Of Radiance", "Brandon Sanderson", painterResource(R.drawable.prueba), LocalDate.ofYearDay(2016,15), 923))
                        searchItem(Book("Words Of Radiance", "Brandon Sanderson", painterResource(R.drawable.prueba), LocalDate.ofYearDay(2016,15), 923))
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

@Preview(name = "LightMode", showBackground = true, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode", showSystemUi = true)
@Composable
fun previewCompsSearch() {
    searchScreen()
}
