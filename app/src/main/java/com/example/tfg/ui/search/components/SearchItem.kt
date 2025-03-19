package com.example.tfg.ui.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.ui.common.BigTittleText
import com.example.tfg.ui.common.SmallTittleText
import com.example.tfg.ui.common.navHost.BookNavigationItems

@Composable
fun SearchItem(book: Book, navigateTo: (String) -> Unit) {
    Box(
        modifier = Modifier
            .clip(AlertDialogDefaults.shape)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .clickable { navigateTo(BookNavigationItems.BookScreen.route)}
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
        ) {

            Row(modifier = Modifier.padding(start = 20.dp, bottom = 10.dp)) {
                Image(
                    painterResource(book.coverImage),
                    contentDescription = stringResource(id = R.string.book_image),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .height(150.dp)
                )
                BasicBookInfo(book)
            }
        }
    }
}

@Composable
private fun BasicBookInfo(book: Book) {
    Column(
        Modifier.padding(start = 10.dp, top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        BigTittleText(book.tittle)
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            SmallTittleText(book.publicationDate.year.toString())
            var pagesToUI = "-"
            if(book.pages != 0){
                pagesToUI = book.pages.toString()
            }
            SmallTittleText(pagesToUI + "pg")
        }
        BigTittleText(book.author)
    }
}