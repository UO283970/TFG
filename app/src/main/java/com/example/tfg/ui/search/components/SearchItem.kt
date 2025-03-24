package com.example.tfg.ui.search.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tfg.model.Book
import com.example.tfg.ui.common.BookAuthorText
import com.example.tfg.ui.common.BookTittleText
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.common.bottonSheetLists.AddBookToListsBottomSheet
import com.example.tfg.ui.common.navHost.BookNavigationItems

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun NewBookSearchItem(
    book: Book,
    stringResourcesProvider: StringResourcesProvider,
    navigateTo: (route: String) -> Unit
) {
    val constraints = LocalConfiguration.current.screenHeightDp.dp
    var bookState by remember { mutableStateOf(book.readingState) }
    var isBottomSheetOpen by remember { mutableStateOf(false) }


    fun changeBookState(listName: String, state: Boolean) {
        bookState = if (state){
            listName
        }else{
            ""
        }
    }

    fun changeCloseOpen() {
        isBottomSheetOpen = !isBottomSheetOpen
    }

    Row(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
            .clickable { navigateTo(BookNavigationItems.BookScreen.route) }
    ) {
        Image(
            painterResource(book.coverImage),
            null,
            modifier = Modifier
                .height(constraints * 0.20f)
                .clip(RoundedCornerShape(16.dp))
        )
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(modifier = Modifier.weight(1f)) {
                    BookTittleText(book.tittle)
                }
                IconButton({ isBottomSheetOpen = true }) {
                    Icon(Icons.Default.Add, null)
                }
            }
            BookAuthorText(book.author)
            Row(verticalAlignment = Alignment.Bottom) {
                if (bookState != "") {
                    Text(bookState, modifier = Modifier.weight(1f))
                }
            }
        }
    }
    if (isBottomSheetOpen) {
        AddBookToListsBottomSheet(
            MaterialTheme.colorScheme.onPrimary, MaterialTheme.colorScheme.onBackground,
            stringResourcesProvider, { changeCloseOpen() },::changeBookState, { navigateTo(it) })
    }
    HorizontalDivider(thickness = 2.dp)
}

