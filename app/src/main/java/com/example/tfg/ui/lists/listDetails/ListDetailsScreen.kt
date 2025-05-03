package com.example.tfg.ui.lists.listDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.ChargingProgress
import com.example.tfg.ui.common.DescText
import com.example.tfg.ui.common.navHost.ListNavigationItems
import com.example.tfg.ui.lists.listDetails.components.ListDetailsItemList
import com.example.tfg.ui.lists.listDetails.components.SearchBarListDetailsScreen
import com.example.tfg.ui.profile.components.statistics.followers.AcceptOperationDialog
import com.example.tfg.ui.theme.TFGTheme
import com.example.tfg.ui.theme.myAppMainFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailsScreen(returnToLastScreen: () -> Unit, navigateTo: (route: String) -> Unit, viewModel: ListDetailsViewModel = hiltViewModel()) {

    LaunchedEffect(viewModel.listDetailsInfo.isDeleted) {
        if(viewModel.listDetailsInfo.isDeleted){
            viewModel.changeMenu(false)
            returnToLastScreen()
        }
    }

    if (viewModel.listDetailsInfo.detailsLoaded) {
        TFGTheme(dynamicColor = false)
        {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = viewModel.listsState.getDetailList().getName(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = myAppMainFont,
                                fontSize = 26.sp
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { returnToLastScreen() },
                                modifier = Modifier.wrapContentSize()
                            ) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back_arrow))
                            }
                        },
                        actions = {
                            if(viewModel.listDetailsInfo.bookList?.canModify() == true){
                                IconButton({viewModel.changeMenu(true)}) {
                                    Icon(Icons.Default.MoreVert,null)
                                }
                                DropdownMenu(viewModel.listDetailsInfo.menuOpen, {viewModel.changeMenu(false)}) {
                                    DropdownMenuItem(
                                        { Text(stringResource(R.string.list_details_dropdown_menu_modify)) },
                                        {
                                            viewModel.changeMenu(false)
                                            navigateTo(ListNavigationItems.ListModify.route)
                                        }
                                    )
                                    DropdownMenuItem(
                                        { Text(stringResource(R.string.list_details_dropdown_menu_delete)) },
                                        {
                                            viewModel.toggleDeleteDialog()
                                        }
                                    )
                                }
                                AcceptOperationDialog(
                                    stringResource(R.string.delete_list_dialog),
                                    close = { viewModel.toggleDeleteDialog() },
                                    accept = {viewModel.deleteList()}
                                )
                            }
                        }
                        ,
                        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
                    )
                }
            ) { innerPadding ->
                Column(Modifier.padding(innerPadding)) {
                    HorizontalDivider(thickness = 1.dp)
                    Column {
                        SearchBarListDetailsScreen(viewModel)
                        if (viewModel.listsState.getDetailList().getDescription() != "") {
                            DescText(3, viewModel.listsState.getDetailList().getDescription())
                            HorizontalDivider(Modifier.padding(top = 5.dp))
                        }
                        ListDetailsItemList(viewModel.listDetailsInfo.baseListOfBooks,{viewModel.setBookForDetails(it)},navigateTo)
                    }
                }
            }
        }
    } else {
        ChargingProgress()
    }

}
