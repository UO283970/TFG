package com.example.tfg.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.ui.common.tittleBigText

@Composable
fun booksCarousel(mainTittle: String, bookList: List<Book>, modifier: Modifier) {

    Column (modifier = modifier,
        ){
        tittleBigText(mainTittle)
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(top = 5.dp).wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            for (book in bookList) {
                bookMainItems(book)
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Outlined.AddCircle, stringResource(R.string.notifications),
                    )
                }
                Text("Ver más")
            }
        }
    }
}

@Composable
fun bookMainItems(book: Book) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .clickable { /*TODO: Se va a la pantalla de detalles del libro*/ }
            .padding(bottom = 10.dp)
    ) {
        Image(
            book.coverImage,
            contentDescription = stringResource(R.string.book_cover) + book.tittle,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp)).weight(1f)
                .width(130.dp),
            contentScale = ContentScale.FillBounds
        )
        bookMainScreenText(book.tittle)
        bookMainScreenText(book.author)
    }
}

@Composable
private fun bookMainScreenText(titulo: String) {
    Text(
        text = titulo,
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 16.sp,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier.widthIn(0.dp, 130.dp)
    )
}