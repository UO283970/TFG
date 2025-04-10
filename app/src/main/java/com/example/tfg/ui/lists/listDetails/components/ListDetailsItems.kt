package com.example.tfg.ui.lists.listDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.tfg.ui.common.BigTittleText
import com.example.tfg.ui.common.SmallTittleText

@Composable
fun ListDetailsItemList(books: ArrayList<Book>) {
    LazyColumn(
        Modifier
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(books){
            ListDetailsItem(it)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ListDetailsItem(book: Book) {
    val constraints = LocalWindowInfo.current.containerSize.height.dp

    Box(
        modifier = Modifier
            .clip(AlertDialogDefaults.shape)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onPrimary)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
        ) {

            Row(modifier = Modifier.padding(start = 20.dp, bottom = 10.dp)) {
                Box(contentAlignment = Alignment.TopEnd) {
                    GlideImage(
                        model = book.coverImage,
                        contentDescription = stringResource(R.string.book_cover)+ " " + book.tittle,
                        loading = placeholder(R.drawable.no_cover_image_book),
                        failure = placeholder(R.drawable.no_cover_image_book),
                        transition = CrossFade,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth(0.25f)
                            .height(constraints * 0.08f),
                        contentScale = ContentScale.FillBounds
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 5.dp, top = 5.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.onPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = if (book.userScore == -1) "-" else book.userScore.toString())
                    }
                }

                Column(
                    Modifier.padding(start = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    BigTittleText(book.tittle)
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        SmallTittleText(book.publicationDate.year.toString())
                        SmallTittleText(if (book.pages != 0) book.pages.toString() + "pg" else "-pg")
                    }
                    BigTittleText(book.author)
                }
            }
        }
    }
}