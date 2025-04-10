package com.example.tfg.ui.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.ListRepository
import com.example.tfg.ui.common.BookAuthorText
import com.example.tfg.ui.common.BookTittleText
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.common.bottonSheetLists.AddBookToListsBottomSheet
import com.example.tfg.ui.common.navHost.BookNavigationItems

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun NewBookSearchItem(
    book: Book,
    stringResourcesProvider: StringResourcesProvider,
    listRepository: ListRepository,
    listsState: ListsState,
    navigateTo: (route: String) -> Unit
) {
    val constraints = LocalWindowInfo.current.containerSize.height.dp
    var bookState by remember { mutableStateOf(book.readingState) }
    var isBottomSheetOpen by remember { mutableStateOf(false) }


    fun changeBookState(listName: DefaultList, state: Boolean) {
        bookState = if (state){
            listName.listName
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
        GlideImage(
            model = book.coverImage,
            contentDescription = stringResource(R.string.book_cover)+ " " + book.tittle,
            loading = placeholder(R.drawable.no_cover_image_book),
            failure = placeholder(R.drawable.no_cover_image_book),
            transition = CrossFade,
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .heightIn(max = constraints * 0.07f)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillBounds
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
            stringResourcesProvider,listRepository, book, listsState, { changeCloseOpen() },::changeBookState, { navigateTo(it) })
    }
    HorizontalDivider(thickness = 2.dp)
}

