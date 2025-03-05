package com.example.tfg.ui.lists.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringArrayResource
import androidx.navigation.NavHostController
import com.example.tfg.R
import com.example.tfg.model.BookList

@Composable
fun tabsLists(navController: NavHostController) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = stringArrayResource(id = R.array.list_of_lists_tabs)

    TabRow(
        selectedTabIndex = tabIndex,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(text = { Text(title) },
                selected = tabIndex == index,
                onClick = { tabIndex = index })
        }
    }
    when (tabIndex) {
        0 -> creteOwnLists(navController, listOf<BookList>(BookList("Fantasia Interesante")))
        1 -> creteDefaultLists(navController)
    }
}