package com.example.tfg.ui.search

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.ChargingProgress
import com.example.tfg.ui.search.components.NewBookSearchItem
import com.example.tfg.ui.search.components.OrderByEnum
import com.example.tfg.ui.search.components.SearchBarSearchScreen
import com.example.tfg.ui.search.components.SearchForEnum
import com.example.tfg.ui.search.components.SubjectsEnum
import com.example.tfg.ui.theme.TFGTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(navigateTo: (route: String) -> Unit, viewModel: SearchViewModel = hiltViewModel()) {
    val sheetState = rememberModalBottomSheetState()
    val listState = rememberLazyListState()

    LaunchedEffect(viewModel.searchInfo.canGetMoreInfo) {
        if (viewModel.searchInfo.canGetMoreInfo) {
            snapshotFlow {
                val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                val totalItems = listState.layoutInfo.totalItemsCount
                (lastVisibleItem?.index ?: 0) >= totalItems - 5
            }.collect { shouldLoadMore ->
                if (shouldLoadMore) {
                    viewModel.addMoreBooksForQuery()
                }
            }
        }
    }

    TFGTheme(dynamicColor = false) {
        Scaffold { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                SearchBarSearchScreen(viewModel) {
                    viewModel.toggleButtonSheet()
                }
                if (viewModel.searchInfo.queryResult.isEmpty() && !viewModel.searchInfo.chargingInfo) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(stringResource(R.string.search_start_message), textAlign = TextAlign.Center)
                    }
                } else {
                    if (viewModel.searchInfo.chargingInfo) {
                        ChargingProgress()
                    } else {
                        Box {
                            LazyColumn(
                                Modifier.padding(start = 10.dp, end = 10.dp), state = listState
                            ) {
                                items(viewModel.searchInfo.queryResult) {
                                    NewBookSearchItem(
                                        it,
                                        viewModel.stringResourcesProvider,
                                        viewModel.listsRepository,
                                        viewModel.listsState,
                                        navigateTo
                                    ) { viewModel.setBookForDetails(it) }
                                }
                            }

                        }
                    }
                }
                if (viewModel.searchInfo.isBottomSheetOpened) {
                    ModalBottomSheet({
                        viewModel.toggleButtonSheet()
                    }, sheetState = sheetState)
                    {
                        Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)) {
                            Text(stringResource(R.string.search_filters_orderBy))
                            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                                var descendingActiveIcon by remember { mutableIntStateOf(R.drawable.sort_descending_icon) }
                                var ascendingActiveIcon by remember { mutableIntStateOf(R.drawable.sort_ascending_icon) }
                                var descending by remember { mutableStateOf(true) }

                                for (order in viewModel.searchInfo.orderByButtonMap) {
                                    var buttonName = stringResource(order.key.getStringResource())

                                    OutlinedButton(
                                        {
                                            viewModel.changeOrderByButton(order.key)
                                            descending = !descending
                                            viewModel.orderBy(order.key, !descending)
                                        }, colors = ButtonDefaults.buttonColors(
                                            containerColor = if (order.value) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
                                        )
                                    ) {
                                        Text(buttonName, color = MaterialTheme.colorScheme.onBackground)
                                        if (viewModel.searchInfo.orderByButtonMap.getValue(order.key) && order.key != OrderByEnum.DEFAULT) {
                                            if (descending) {
                                                Icon(
                                                    painterResource(descendingActiveIcon),
                                                    null,
                                                    modifier = Modifier.size(22.dp),
                                                    tint = MaterialTheme.colorScheme.onBackground
                                                )
                                            } else {
                                                Icon(
                                                    painterResource(ascendingActiveIcon),
                                                    null,
                                                    modifier = Modifier.size(22.dp),
                                                    tint = MaterialTheme.colorScheme.onBackground
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            Text(stringResource(R.string.search_filters_search_for))
                            Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                                for (searchFor in SearchForEnum.entries) {
                                    OutlinedButton(
                                        { viewModel.changeSelectedSearchFor(searchFor) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (searchFor == viewModel.searchInfo.searchFor) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
                                        )
                                    ) {
                                        Text(stringResource(searchFor.stringResource()), color = MaterialTheme.colorScheme.onBackground)
                                    }
                                }
                            }
                            Text(stringResource(R.string.search_filters_search_by_gender))
                            FlowRow(maxItemsInEachRow = 3, horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                                var actualNamedList = linkedMapOf<SubjectsEnum, Int>()
                                for (subject in SubjectsEnum.entries) {
                                    actualNamedList.put(subject, subject.getIconResource())
                                }
                                for (subject in actualNamedList.toSortedMap()) {
                                    OutlinedButton(
                                        { viewModel.changeSelectedSubject(subject.key) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (viewModel.searchInfo.subject != null && subject.key == viewModel.searchInfo.subject) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
                                        )
                                    ) {
                                        Icon(
                                            painterResource(subject.value),
                                            null,
                                            modifier = Modifier.size(22.dp),
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                        Text(stringResource(subject.key.getStringResource()), color = MaterialTheme.colorScheme.onBackground)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
